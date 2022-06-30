package com.find.presentation.detailUI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.find.presentation.BaseFragment
import com.find.presentation.R
import com.find.presentation.databinding.FragmentDetailBinding
import com.find.presentation.launchAndCollectIn
import com.find.presentation.detailUI.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!findNavController().popBackStack()) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })

        viewModel.viewState.launchAndCollectIn(
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        ) { viewState ->
            binding.tvId.text=viewState.id
            binding.tvTitle.text = viewState.title
            binding.tvAddress.text = viewState.address
            binding.tvAmountChargers.text = viewState.amountCharges

        }
        setupNavigation()
    }
    private fun setupNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

}