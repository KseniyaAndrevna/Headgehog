package com.kseniyaa.headgehog

import com.kseniyaa.headgehog.model.Result
import com.kseniyaa.headgehog.model.ResultCount
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("jokes/random/{value}")
    fun getItems(@Path("value") type: Int?): Call<Result>

    @GET("jokes/count")
    fun getJokesCount(): Call<ResultCount>
}