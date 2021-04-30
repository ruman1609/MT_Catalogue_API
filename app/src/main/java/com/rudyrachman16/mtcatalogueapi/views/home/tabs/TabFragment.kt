package com.rudyrachman16.mtcatalogueapi.views.home.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rudyrachman16.mtcatalogueapi.data.api.models.MovieList
import com.rudyrachman16.mtcatalogueapi.data.api.models.TvShowList
import com.rudyrachman16.mtcatalogueapi.databinding.FragmentTabBinding
import com.rudyrachman16.mtcatalogueapi.views.detail.DetailActivity
import com.rudyrachman16.mtcatalogueapi.views.home.HomeAdapter
import com.rudyrachman16.mtcatalogueapi.views.viewmodel.ViewModelFactory

class TabFragment : Fragment() {
    companion object {
        private const val POS_KEY = "com.rudyrachman16.mtcatalogue.views.home.tabs.pos_key"

        @JvmStatic
        fun newInstance(position: Int): TabFragment = TabFragment().apply {
            arguments = Bundle().apply { putInt(POS_KEY, position) }
        }
    }

    private var binding: FragmentTabBinding? = null
    private val bind get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(POS_KEY)
        if (position != null) {
            val viewModel: TabViewModel by viewModels {
                ViewModelFactory.getInstance(requireActivity().application)
            }
            when (position) {
                0 -> {
                    val rvAdapter = HomeAdapter<MovieList>({ number, title, rating, voter ->
                        HomeAdapter.share(requireActivity(), position, number, title, rating, voter)
                    }) { id, number, title ->
                        startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                            putExtra(DetailActivity.KEY_MOV, id)
                            putExtra(DetailActivity.KEY_POS, position)
                            putExtra(DetailActivity.KEY_NUM, number)
                            putExtra(DetailActivity.KEY_TITLE, title)
                        })
                    }
                    viewModel.getMovies().observe(requireActivity()) {
                        rvAdapter.setList(it)
                        makeRV(rvAdapter)
                    }
                }
                1 -> {
                    val rvAdapter = HomeAdapter<TvShowList>({ number, title, rating, voter ->
                        HomeAdapter.share(requireActivity(), position, number, title, rating, voter)
                    }) { id, number, title ->
                        startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                            putExtra(DetailActivity.KEY_TV, id)
                            putExtra(DetailActivity.KEY_POS, position)
                            putExtra(DetailActivity.KEY_NUM, number)
                            putExtra(DetailActivity.KEY_TITLE, title)
                        })
                    }
                    viewModel.getTvShow().observe(requireActivity()) {
                        rvAdapter.setList(it)
                        makeRV(rvAdapter)
                    }
                }
            }
        }
    }

    private fun <T> makeRV(rvAdapter: HomeAdapter<T>) {
        bind.tabLoading.visibility = View.GONE
        bind.tabRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}