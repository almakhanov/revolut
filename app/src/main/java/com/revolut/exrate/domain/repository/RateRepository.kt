package com.revolut.exrate.domain.repository

import com.revolut.exrate.data.remote.ApiInteractor
import com.revolut.exrate.data.remote.CoroutineCaller
import com.revolut.exrate.data.remote.OpenApi

class RateRepository(private val openApi: OpenApi): CoroutineCaller by ApiInteractor(){

    suspend fun getRate(baseRate: String) = coroutineApiCall(openApi.getRate(baseRate))

}