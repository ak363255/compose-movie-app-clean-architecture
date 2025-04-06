package com.example.data.netowrk

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieApi {
    @GET
    suspend fun getRequest(@Url url: String): retrofit2.Response<ResponseBody>
}