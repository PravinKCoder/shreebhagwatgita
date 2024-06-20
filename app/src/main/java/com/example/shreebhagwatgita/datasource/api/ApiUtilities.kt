package com.example.shreebhagwatgita.datasource.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {

    val headers = mapOf<String, String>(
        "Accept" to "application/json",
        "X-RapidAPI-Key" to "2f28e3bc79mshea5b8149b0b34b5p1da17cjsn4813a5f0a27b",
        "X-RapidAPI-Host" to "bhagavad-gita3.p.rapidapi.com"
    )

    private val client = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val newReq = chain.request().newBuilder().apply {
                headers.forEach { (key, value) -> addHeader(key, value) }
            }.build()
            chain.proceed(newReq)
        }
    }.build()
    val api: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://bhagavad-gita3.p.rapidapi.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}