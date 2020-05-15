package com.ayush.noonacademy.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ayush.noonacademy.app.App
import com.ayush.noonacademy.app.RepoComponent
import com.ayush.noonacademy.R
import com.ayush.noonacademy.base.BaseActivity
import com.ayush.noonacademy.databinding.ActivitySearchBinding
import javax.inject.Inject

class SearchActivity:  BaseActivity() {
    @Inject
    lateinit var searchViewModel: SearchViewModel
    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        di()
        ui()
        searchViewModel.responseLiveData().observe(this, Observer {
            searchViewModel.handleData(it)
        })
        searchViewModel.listLiveData().observe(this, Observer {

        })
    }

    private fun ui() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.vm = searchViewModel
    }

    private fun di() {
       DaggerSearchComponent.factory().create(App.get(this).getAppComponent() as RepoComponent).inject(this)
    }
}