package com.siddevlops.cryptoprj.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.siddevlops.cryptoprj.R
import com.siddevlops.cryptoprj.core.common.BaseActivity
import com.siddevlops.cryptoprj.core.common.NavigationHost
import com.siddevlops.cryptoprj.util.ThemeHelper
import com.siddevlops.cryptoprj.util.ThemeMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

@AndroidEntryPoint
class HomeActivity : BaseActivity(),
    NavigationHost {

    private val viewModel: HomeActivityViewModel by viewModels()

    companion object {
        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_coins_list,
            R.id.navigation_favourites,
            R.id.navigation_settings
        )
    }

    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var adView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.applyTheme(if (viewModel.isDarkModeOn()) ThemeMode.Dark else ThemeMode.Light)
        setContentView(R.layout.activity_main)



        // loading ad //

        // Load an ad into the AdMob banner view.
        adView = findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        try{
            adView.adSize = AdSize.BANNER
            adView.adUnitId = R.string.BANNER_AD.toString()
        }catch (e:Exception){
            e.printStackTrace()
        }

        adView.loadAd(adRequest)



        adView.adListener = object : AdListener() {


            override fun onAdLoaded() {
                super.onAdLoaded()
                val toastMessage: String = "ad loaded"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                val toastMessage: String = "ad is open"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onAdClicked() {
                super.onAdClicked()
                val toastMessage: String = "ad is clicked"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                val toastMessage: String = "ad is closed"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }


            navHostFragment =supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as? NavHostFragment ?: return

        navController = findNavController(R.id.homeNavHostFragment)
        appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS)
        homeBottomNavView.setupWithNavController(navController)

    }


        //Callback method to update the toolbar's title based on the selected bottom tab
    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}