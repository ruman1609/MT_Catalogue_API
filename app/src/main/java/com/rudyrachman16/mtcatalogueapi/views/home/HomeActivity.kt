package com.rudyrachman16.mtcatalogueapi.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.rudyrachman16.mtcatalogueapi.databinding.ActivityHomeBinding
import com.rudyrachman16.mtcatalogueapi.views.home.tabs.TabAdapter

class HomeActivity : AppCompatActivity() {
    private var binding: ActivityHomeBinding? = null
    private val bind get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setSupportActionBar(bind.toolbar.root)
        val tabAdapter = TabAdapter(this)
        bind.viewPager.adapter = tabAdapter
        TabLayoutMediator(bind.tabLayout, bind.viewPager) { tab, position ->
            tab.text = getText(TabAdapter.TAB_TITLE[position])
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}