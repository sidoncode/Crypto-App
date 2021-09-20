package com.siddevlops.cryptoprj.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.siddevlops.cryptoprj.core.common.BaseViewModel
import com.siddevlops.cryptoprj.data.repository.settings.SettingsRepository

class HomeActivityViewModel @ViewModelInject constructor(private val repository: SettingsRepository) : BaseViewModel() {

    fun isDarkModeOn () : Boolean{
        return repository.isDarkModeEnabled()
    }
}