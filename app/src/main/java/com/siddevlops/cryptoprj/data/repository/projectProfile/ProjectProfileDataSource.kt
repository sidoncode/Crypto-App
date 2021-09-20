package com.siddevlops.cryptoprj.data.repository.projectProfile

import com.siddevlops.cryptoprj.data.local.database.CoinsDatabase
import javax.inject.Inject

class ProjectProfileDataSource @Inject constructor(private val db: CoinsDatabase){

    fun projectBySymbol(symbol: String) = db.coinsListDao().projectLiveDataFromSymbol(symbol)

}