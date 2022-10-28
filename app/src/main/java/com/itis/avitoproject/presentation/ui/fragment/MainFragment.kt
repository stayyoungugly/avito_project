package com.itis.avitoproject.presentation.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.snackbar.Snackbar
import com.itis.avitoproject.R
import com.itis.avitoproject.databinding.FragmentMainBinding
import com.itis.avitoproject.presentation.viewmodel.MainFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val mainViewModel: MainFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        mainViewModel.getCityName()
    }

    private fun initObservers() {
        mainViewModel.cityName.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) checkPermission()
            else {
                val bundle = bundleOf("name" to it)
                findNavController().navigate(
                    R.id.action_mainFragment_to_currentWeatherFragment,
                    bundle
                )
            }
        }
        mainViewModel.error.observe(viewLifecycleOwner) {
            Timber.e(it)
            showMessage(getString(R.string.fail))
        }
    }

    private fun getLocationPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun checkPermission() {
        if (mainViewModel.isPermissionsAllowed()) {
            sendLocation()
        } else getLocationPermissions()
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                sendLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                sendLocation()
            }
            else -> {
                findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendLocation() {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        fusedLocationClient.getCurrentLocation(
            PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(token: OnTokenCanceledListener) =
                    CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })

            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    val bundle = bundleOf("message" to getString(R.string.city_not_found))
                    findNavController().navigate(R.id.action_mainFragment_to_searchFragment, bundle)
                } else {
                    val lat = location.latitude.toString()
                    val lon = location.longitude.toString()
                    val bundle = bundleOf("lat" to lat, "lon" to lon)
                    findNavController().navigate(
                        R.id.action_mainFragment_to_currentWeatherFragment,
                        bundle
                    )
                }

            }
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
