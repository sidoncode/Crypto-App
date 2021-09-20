package com.siddevlops.cryptoprj.ui.home.favoruites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.siddevlops.cryptoprj.R
import com.siddevlops.cryptoprj.adapters.CoinsListAdapter
import com.siddevlops.cryptoprj.adapters.OnItemClickCallback
import com.siddevlops.cryptoprj.databinding.FragmentFavoritesBinding
import com.siddevlops.cryptoprj.core.common.MainNavigationFragment
import com.siddevlops.cryptoprj.ui.projectProfile.ProjectProfileActivity
import com.siddevlops.cryptoprj.util.Constants
import com.siddevlops.cryptoprj.util.extensions.doOnChange
import kotlinx.android.synthetic.main.fragment_favorites.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : MainNavigationFragment(), OnItemClickCallback {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    private var favouritesAdapter = CoinsListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@FavoritesFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }


    override fun initializeViews() {
        favouritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouritesAdapter
        }
    }

    override fun observeViewModel() {
        viewModel.favoriteCoinsList.doOnChange(this) {
            favouritesAdapter.updateList(it)

            if (it.isEmpty()) {
                viewModel.isFavouritesEmpty(true)
            }
        }

        viewModel.toastError.doOnChange(this) {
            showToast(it)
        }

        viewModel.favouriteStock.doOnChange(this) {
            showToast(
                getString(if (it.isFavourite) R.string.added_to_favourite else R.string.removed_to_favourite).format(
                    it.symbol
                )
            )
        }
    }

    override fun onItemClick(symbol: String, id: String) {
        requireActivity().run {
            startActivity(
                Intent(this, ProjectProfileActivity::class.java)
                    .apply {
                        putExtra(Constants.EXTRA_SYMBOL, symbol)
                        putExtra(Constants.EXTRA_SYMBOL_ID, id)
                    }
            )
        }

    }

    override fun onFavouriteClicked(symbol: String) {
        viewModel.updateFavouriteStatus(symbol)
    }
}