package com.itis.avitoproject.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.itis.avitoproject.R
import com.itis.avitoproject.databinding.FragmentCurrentBinding
import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.entity.WeatherFullDay
import com.itis.avitoproject.presentation.ui.rv.TimeOfDayTempAdapter
import com.itis.avitoproject.presentation.viewmodel.CurrentWeatherFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CurrentWeatherFragment : Fragment(R.layout.fragment_current) {
    private val binding by viewBinding(FragmentCurrentBinding::bind)

    private val currentViewModel: CurrentWeatherFragmentViewModel by viewModel()

    private val tempsAdapter: TimeOfDayTempAdapter by lazy {
        TimeOfDayTempAdapter()
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }
    private var name: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding.rvTemps.adapter = tempsAdapter
        openBundle()
        with(binding) {
            ltFail.btnFail.setOnClickListener {
                openBundle()
            }
            btnWeek.setOnClickListener {
                val bundle = bundleOf("name" to name)
                findNavController().navigate(R.id.action_currentDayFragment_to_weekFragment, bundle)
            }

            btnChange.setOnClickListener {
                findNavController().navigate(R.id.action_currentDayFragment_to_searchFragment)
            }
        }
    }

    private fun openBundle() {
        isFail(false)

        binding.ltLoading.loading.visibility = View.VISIBLE
        binding.ltDetails.visibility = View.GONE

        val lon = arguments?.getString("lon")
        val lat = arguments?.getString("lat")
        val newName = arguments?.getString("name")
        var weatherData: CurrentWeather? = null
        if (arguments?.get("weatherData") != null) {
            weatherData = arguments?.get("weatherData") as CurrentWeather
        }

        if (lon != null) {
            if (lat != null) {
                currentViewModel.getCurrentWeatherByCoordinates(lat, lon)
                currentViewModel.getDayWeatherByCoordinates(lat, lon)
            }
        }
        if (newName != null) {
            name = newName
            currentViewModel.getCurrentWeatherByName(name)
            currentViewModel.getDayWeatherByName(name)
        }

        if (weatherData != null) {
            name = weatherData.cityName
            setWeather(weatherData)
        }
    }

    private fun initObservers() {
        currentViewModel.currentWeather.observe(viewLifecycleOwner) { weatherInfo ->
            weatherInfo.fold(onSuccess = {
                name = it.cityName
                setWeather(it)
            }, onFailure = {
                binding.ltLoading.loading.visibility = View.GONE
                showMessage(getString(R.string.fail))
                isFail(true)
            })
        }
        currentViewModel.dayWeather.observe(viewLifecycleOwner) { weatherInfo ->
            weatherInfo.fold(onSuccess = {
                println(it)
                setDayWeather(it)
                binding.ltLoading.loading.visibility = View.GONE
                binding.ltDetails.visibility = View.VISIBLE
            }, onFailure = {
                binding.ltLoading.loading.visibility = View.GONE
                showMessage(getString(R.string.fail))
                isFail(true)
            })
        }
        currentViewModel.error.observe(viewLifecycleOwner) {
            Timber.e(it)
        }
    }

    private fun setDayWeather(fullDay: WeatherFullDay) {
        val list = fullDay.listTimesOfDayTemp
        tempsAdapter.submitList(list.toMutableList())
    }

    private fun setWeather(currentWeather: CurrentWeather) {
        with(binding) {
            tvCityName.text = currentWeather.cityName
            ltFullDetails.tvWindNumber.text = currentWeather.windSpeed + " " + "м/c"
            ltFullDetails.tvDirectionPeak.text = currentWeather.windDirection
            ltFullDetails.tvHumidityNumber.text = currentWeather.humidity.toString() + "%"
            ltFullDetails.tvPressureNumber.text = currentWeather.pressure.toString() + "гПа"
            tvTempFeels.text = "Ощущается как " + currentWeather.feelsLikeTemp + " " + "°C"
            tvTemp.text = currentWeather.temp + " " + "°C"
            ltFullDetails.tvMinTemp.text = currentWeather.minTemp + " " + "°C"
            ltFullDetails.tvMaxTemp.text = currentWeather.maxTemp + " " + "°C"
            tvDescription.text = currentWeather.weatherDescription

            val weatherIcon = currentWeather.weatherIcon
            val options = RequestOptions()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            glide.load(generateIcon(weatherIcon))
                .apply(options)
                .into(ivIcon)
        }
        currentViewModel.getDayWeatherByName(currentWeather.cityName)
    }

    private fun generateIcon(iconId: String): String {
        val url = "http://openweathermap.org/img/wn/$iconId@2x.png"
        print(url)
        return url
    }


    private fun isFail(flag: Boolean) {
        with(binding) {
            if (flag) {
                binding.ltDetails.visibility = View.GONE
                binding.ltFail.fail.visibility = View.VISIBLE
            } else {
                binding.ltDetails.visibility = View.VISIBLE
                binding.ltFail.fail.visibility = View.GONE
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
