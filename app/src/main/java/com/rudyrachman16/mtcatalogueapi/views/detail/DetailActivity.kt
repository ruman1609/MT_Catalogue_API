package com.rudyrachman16.mtcatalogueapi.views.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rudyrachman16.mtcatalogueapi.BuildConfig
import com.rudyrachman16.mtcatalogueapi.R
import com.rudyrachman16.mtcatalogueapi.data.api.models.Genre
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieDetail
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowDetail
import com.rudyrachman16.mtcatalogueapi.databinding.ActivityDetailBinding
import com.rudyrachman16.mtcatalogueapi.views.home.HomeAdapter
import com.rudyrachman16.mtcatalogueapi.views.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val KEY_MOV = "com.rudyrachman16.mtcatalogue.views.detail.key_mov"
        const val KEY_TV = "com.rudyrachman16.mtcatalogue.views.detail.key_tv"
        const val KEY_POS = "com.rudyrachman16.mtcatalogue.views.detail.key_pos"
        const val KEY_NUM = "com.rudyrachman16.mtcatalogue.views.detail.key_num"
        const val KEY_TITLE = "com.rudyrachman16.mtcatalogue.views.detail.key_title"
    }

    private var position = -1

    private var binding: ActivityDetailBinding? = null
    private val bind get() = binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setSupportActionBar(bind.toolbar)
        title = intent.extras?.getString(KEY_TITLE)
        val color =
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) resources.getColor(R.color.white)
            else resources.getColor(R.color.white, null)
        bind.detCollapse.apply {
            setExpandedTitleColor(color)
            setExpandedTitleTextAppearance(R.style.Text_H1)
            setStatusBarScrimColor(color)
            setCollapsedTitleTextColor(color)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.extras?.getString(KEY_MOV)
        val tvShow = intent.extras?.getString(KEY_TV)
        position = intent.getIntExtra(KEY_POS, -1)
        if (movie != null || tvShow != null) {
            if (movie != null) {
                val viewModel: DetailViewModel by viewModels {
                    ViewModelFactory.getInstance(application, movie)
                }
                viewModel.getDetail<MovieDetail>(DetailViewModel.MOVIE).observe(this) {
                    initMovie(it)
                }
            } else if (tvShow != null) {
                val viewModel: DetailViewModel by viewModels {
                    ViewModelFactory.getInstance(application, tvShow)
                }
                viewModel.getDetail<TvShowDetail>(DetailViewModel.TV_SHOW).observe(this) {
                    initTvShow(it)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initTvShow(tvShow: TvShowDetail) {
        initAll(
            tvShow.title, tvShow.imgUrl, tvShow.bgUrl, tvShow.desc,
            tvShow.rating, tvShow.voter, tvShow.genres
        )
        bind.detType.text = getString(
            R.string.tv_show,
            if (tvShow.episodes == 1) "1 episode" else "${tvShow.episodes} episodes",
            if (tvShow.seasons == 1) "1 season" else "${tvShow.seasons} seasons",
            tvShow.status
        )
        bind.detAirTitle.text = getString(R.string.first_air)
        bind.detDate.text = HomeAdapter.parseDate(tvShow.date)
        initCompany(tvShow)
    }

    private fun initMovie(movie: MovieDetail) {
        initAll(
            movie.title, movie.imgUrl, movie.bgUrl, movie.desc,
            movie.rating, movie.voter, movie.genres
        )
        bind.detType.text = getString(R.string.movie)
        bind.detAirTitle.text = getString(R.string.release_date)
        bind.detDate.text = HomeAdapter.parseDate(movie.date)
        initCompany(movie)
    }

    private fun initAll(
        title: String, imgURL: String, bgURL: String, desc: String,
        rating: Double, voter: Int, genres: ArrayList<Genre>
    ) {
        bind.detLoading.visibility = View.GONE
        bind.detAppBarLayout.visibility = View.VISIBLE
        bind.detShare.visibility = View.VISIBLE
        bind.detNested.visibility = View.VISIBLE
        bind.detRating.text = rating.toString()
        bind.detUserRate.text = getString(R.string.user_rate, voter)
        Glide
            .with(applicationContext).load(BuildConfig.BASE_IMAGE + imgURL)
            .apply(RequestOptions.placeholderOf(R.drawable.loading))
            .error(R.drawable.error).into(bind.detImg)
        Glide
            .with(applicationContext).load(BuildConfig.BASE_IMAGE + bgURL)
            .apply(RequestOptions.placeholderOf(R.drawable.loading))
            .error(R.drawable.error).into(bind.detBg)
        bind.detShare.setOnClickListener {
            HomeAdapter.share(
                this, position, intent.getIntExtra(KEY_NUM, 0),
                title, rating, voter
            )
        }
        bind.detOverview.text = getString(R.string.overview_in_english_only, title)
        bind.detDesc.text = desc
        genres.forEachIndexed { index, it ->
            bind.detGenres.append("${index + 1}. ${it.name}\n")
        }
    }

    private fun <T> initCompany(model: T) {
        val detailAdapter = DetailAdapter()
        var available = false
        when (model) {
            is MovieDetail -> {
                if (model.companies.isNotEmpty()) {
                    available = true
                    detailAdapter.setList(model.companies)
                }
            }
            is TvShowDetail -> {
                if (model.companies.isNotEmpty()) {
                    available = true
                    detailAdapter.setList(model.companies)
                }
            }
        }
        if (available) {
            bind.detCompanies.apply {
                val manager = LinearLayoutManager(applicationContext)
                manager.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager = manager
                adapter = detailAdapter
                setHasFixedSize(true)
            }
        } else bind.detCompany.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}