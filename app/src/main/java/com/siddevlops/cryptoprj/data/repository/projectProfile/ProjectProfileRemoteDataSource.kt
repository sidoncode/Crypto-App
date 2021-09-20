package com.siddevlops.cryptoprj.data.repository.projectProfile

import com.siddevlops.cryptoprj.api.ApiInterface
import com.siddevlops.cryptoprj.api.BaseRemoteDataSource
import javax.inject.Inject
import com.siddevlops.cryptoprj.api.Result
import com.siddevlops.cryptoprj.api.models.HistoricalPriceResponse

class ProjectProfileRemoteDataSource @Inject constructor(private val service: ApiInterface) : BaseRemoteDataSource(){

    //fetch historical price from backend
    suspend fun historicalPrice(symbol: String, targetCurrency: String, days: Int = 30): Result<HistoricalPriceResponse> =
        getResult { service.historicalPrice(symbol, targetCurrency, days) }

}