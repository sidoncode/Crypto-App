package com.siddevlops.cryptoprj.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.siddevlops.cryptoprj.databinding.FragmentSettingsBinding
import com.siddevlops.cryptoprj.core.common.MainNavigationFragment
import com.siddevlops.cryptoprj.util.ThemeHelper
import com.siddevlops.cryptoprj.util.ThemeMode
import com.siddevlops.cryptoprj.util.extensions.doOnChange
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettingsFragment : MainNavigationFragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@SettingsFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun initializeViews() {

    }

    override fun observeViewModel() {
        viewModel.isDarkMode.doOnChange(this) {
            Timber.d("On Theme changed")
            ThemeHelper.applyTheme(if (it) ThemeMode.Dark else ThemeMode.Light)
        }
    }
}