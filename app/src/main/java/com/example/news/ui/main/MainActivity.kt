package com.example.news.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.news.R
import com.example.news.api.ApiManager
import com.example.news.api.model.SourcesResponse
import com.example.news.databinding.ActivityMainBinding
import com.example.news.ui.main.fragments.News.NewsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container , NewsFragment() , "").commit()
    }
}