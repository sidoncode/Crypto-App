package com.siddevlops.cryptoprj.data.repository.coinsList

import com.siddevlops.cryptoprj.api.ApiInterface
import com.siddevlops.cryptoprj.api.BaseRemoteDataSource
import javax.inject.Inject
import com.siddevlops.cryptoprj.api.Result
import com.siddevlops.cryptoprj.api.models.Coin

class CoinsListRemoteDataSource @Inject constructor(private val service: ApiInterface) :
    BaseRemoteDataSource() {

    suspend fun coinsList(targetCur: String): Result<List<Coin>> =
        getResult {
            service.coinsList(targetCur)
        }
}