package com.example.news.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {
    companion object{
        const val apiKey = "ae228fac4d9a4af8b3a4404665b12e18"
        private var retrofit: Retrofit? = null

        fun getWebServices(): WebServices {


            if (retrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor {
                    Log.e("ApiManager", "Body:$it")
                }
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val  okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit!!.create(WebServices::class.java)
        }
    }

}