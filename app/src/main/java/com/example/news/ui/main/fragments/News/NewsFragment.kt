package com.example.news.ui.main.fragments.News

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.news.api.ApiManager
import com.example.news.api.model.ArticlesResponse
import com.example.news.api.model.Source
import com.example.news.api.model.SourcesResponse
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.ui.main.fragments.News.adapters.ArticlesAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment(), OnTabSelectedListener {
    lateinit var binding: FragmentNewsBinding
    var adapter = ArticlesAdapter(listOf())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSources()
        initListeners()
        intiRecyclerView()
    }

    private fun intiRecyclerView() {
        binding.articlesRecyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.errorView.retryBtn.setOnClickListener {
            loadSources()

        }
        binding.tabLayout.addOnTabSelectedListener(this)
    }

    private fun loadSources() {
        changeLoaderVisibility(true)
        changeErrorVisibility(isVisible = false)
        ApiManager.getWebServices().getSources(ApiManager.apiKey)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    changeLoaderVisibility(isVisible = false)
                    if (response.isSuccessful) {
                        response.body()?.sources.let {
                            showTabs(it!!)
                        }
                    } else {
                        changeErrorVisibility(true)
                        val response = Gson().fromJson(
                            response.errorBody()?.string(),
                            SourcesResponse::class.java
                        )
                        changeErrorVisibility(true, "Something went wrong please try again later")
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    changeLoaderVisibility(isVisible = false)
                    changeErrorVisibility(
                        true,
                        t.localizedMessage ?: "Something went wrong please try again later"
                    )
                }

            })
    }


    private fun showTabs(sources: List<Source?>) {
        sources.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source?.name
            binding.tabLayout.addTab(tab)
            tab.tag = source
        }
        binding.tabLayout.getTabAt(0)?.select()
    }

    private fun changeErrorVisibility(isVisible: Boolean, message: String = "") {
        binding.errorView.root.isVisible = isVisible
        if (isVisible) {
            binding.errorView.errorTv.text = message
        }
    }

    private fun changeLoaderVisibility(isVisible: Boolean) {
        binding.loadingView.isVisible = isVisible
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val source = tab?.tag as Source
        source.id?.let {
            loadArticles(it)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        val source = tab?.tag as Source?
        source?.id?.let {
            loadArticles(it)
        }
    }

    private fun loadArticles(sourceId: String) {
        ApiManager.getWebServices().getArticles(
            ApiManager.apiKey,
            sourceId
        ).enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful && response.body()?.articles?.isNotEmpty() == true) {
                        adapter.update(response.body()?.articles!!)
                } else {
                    changeErrorVisibility(true)
                    val response = Gson().fromJson(
                        response.errorBody()?.string(),
                        SourcesResponse::class.java
                    )
                    changeErrorVisibility(true, "Something went wrong please try again later")
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                changeLoaderVisibility(isVisible = false)
                changeErrorVisibility(
                    true,
                    t.localizedMessage ?: "Something went wrong please try again later"
                )
            }

        })
    }
}