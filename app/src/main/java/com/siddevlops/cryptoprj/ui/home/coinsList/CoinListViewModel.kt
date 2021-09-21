package com.siddevlops.cryptoprj.ui.home.coinsList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.siddevlops.cryptoprj.core.common.BaseViewModel
import com.siddevlops.cryptoprj.data.repository.coinsList.CoinsListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.siddevlops.cryptoprj.api.Result
import com.siddevlops.cryptoprj.data.local.database.CoinsListEntity

class CoinListViewModel @ViewModelInject constructor(private val repository: CoinsListRepository) :
    BaseViewModel() {

    val coinsListData = repository.allCoinsLD

    //LiveData to show add/remove status as toast message
    private val _favouriteStock = MutableLiveData<CoinsListEntity?>()
    val favouriteStock: LiveData<CoinsListEntity?> = _favouriteStock


    fun isListEmpty(): Boolean {
        return coinsListData.value?.isEmpty() ?: true
    }

    fun loadCoinsFromApi(targetCur: String = "INR") {
        if (repository.loadData()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                repository.coinsList(targetCur)
                _isLoading.postValue(false)
            }
        }
    }

    fun updateFavouriteStatus(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateFavouriteStatus(symbol)
            when (result) {
                is Result.Success -> _favouriteStock.postValue(result.data)
                is Result.Error -> _toastError.postValue(result.message)
            }
        }
    }

}