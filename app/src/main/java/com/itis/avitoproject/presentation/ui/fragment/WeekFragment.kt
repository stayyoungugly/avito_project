package com.itis.avitoproject.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.itis.avitoproject.R
import com.itis.avitoproject.databinding.FragmentWeekBinding
import com.itis.avitoproject.domain.entity.WeatherFullDay
import com.itis.avitoproject.presentation.ui.rv.TimeOfDayTempAdapter
import com.itis.avitoproject.presentation.ui.rv.WeatherFullDayAdapter
import com.itis.avitoproject.presentation.viewmodel.WeekFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class WeekFragment : Fragment(R.layout.fragment_week) {

    private val binding by viewBinding(FragmentWeekBinding::bind)

    private val weekViewModel: WeekFragmentViewModel by viewModel()

    private var name: String = ""

    private val weekAdapter: WeatherFullDayAdapter by lazy {
        WeatherFullDayAdapter(glide) { weather -> navigateToFullInfo(weather) }
    }

    private fun navigateToFullInfo(weather: WeatherFullDay) {
        val bundle = bundleOf("weatherData" to weather)
        findNavController().navigate(R.id.action_weekFragment_to_dayFragment, bundle)
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding.rvTemps.adapter = weekAdapter
        val newName = arguments?.getString("name")

        if (newName != null) {
            name = newName
            binding.ltLoading.loading.visibility = View.VISIBLE
            binding.ltDetails.visibility = View.GONE
            isFail(false)
            weekViewModel.getWeekWeatherByName(name)
        }

        with(binding) {
            btnFail.setOnClickListener {
                ltLoading.loading.visibility = View.VISIBLE
                ltDetails.visibility = View.GONE
                isFail(false)
                weekViewModel.getWeekWeatherByName(name)
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun initObservers() {
        weekViewModel.weekWeather.observe(viewLifecycleOwner) { weatherInfo ->
            weatherInfo.fold(onSuccess = {
                setWeatherInfo(it)
                isFail(false)
                binding.ltLoading.loading.visibility = View.GONE
                binding.ltDetails.visibility = View.VISIBLE
            }, onFailure = {
                isFail(true)
                binding.ltLoading.loading.visibility = View.GONE
                showMessage(getString(R.string.fail))
            })
        }
        weekViewModel.error.observe(viewLifecycleOwner) {
            Timber.e(it)
        }
    }

    private fun setWeatherInfo(weatherList: List<WeatherFullDay>) {
        weekAdapter.submitList(weatherList.toMutableList())
    }

    private fun isFail(flag: Boolean) {
        with(binding) {
            if (flag) {
                binding.ltDetails.visibility = View.GONE
                binding.btnFail.visibility = View.VISIBLE
                binding.tvFail.visibility = View.VISIBLE
            } else {
                binding.ltDetails.visibility = View.VISIBLE
                binding.btnFail.visibility = View.GONE
                binding.tvFail.visibility = View.GONE
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
