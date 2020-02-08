package com.revolut.exrate.data.remote

import kotlinx.coroutines.Deferred
import com.revolut.exrate.domain.entity.BaseRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApi{

    @GET("latest")
    fun getRate(@Query("base") base: String): Deferred<Response<BaseRate>>
}