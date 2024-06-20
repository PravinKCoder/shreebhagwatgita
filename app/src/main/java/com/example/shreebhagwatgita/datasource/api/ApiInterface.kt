package com.example.shreebhagwatgita.datasource.api

import com.example.shreebhagwatgita.model.Chapters
import com.example.shreebhagwatgita.model.ChaptersItem
import com.example.shreebhagwatgita.model.VersesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {
    /*   @Headers(
           "Accept: application/json",
           "X-RapidAPI-Key: 2f28e3bc79mshea5b8149b0b34b5p1da17cjsn4813a5f0a27b",
           "X-RapidAPI-Host: bhagavad-gita3.p.rapidapi.com"
       )*/

    @GET("/v2/chapters/")
    fun getAllChapters(): Call<List<ChaptersItem>>

    @GET("v2/chapters/{chapterNumber}/verses/")
    fun getVerses(@Path("chapterNumber") chapterNumber: Int): Call<List<VersesItem>>

    @GET("v2/chapters/{chapterNumber}/verses/{verseNo}/")
    fun getParticularVerse(
        @Path("chapterNumber") chapterNumber: Int,
        @Path("verseNo") verseNo: Int
    ): Call<VersesItem>
}