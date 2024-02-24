package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.news.api.ApiManager
import com.example.news.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApiManager().getWebServices().getSources("ae228fac4d9a4af8b3a4404665b12e18")
            .enqueue(object : Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                }

            })
    }
}