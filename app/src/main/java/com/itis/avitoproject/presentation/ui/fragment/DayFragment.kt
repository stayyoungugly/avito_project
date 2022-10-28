package com.itis.avitoproject.presentation.ui.fragment

import android.os.Bundle
import android.view.View
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
import com.itis.avitoproject.databinding.FragmentDayBinding
import com.itis.avitoproject.domain.entity.CurrentWeather
import com.itis.avitoproject.domain.entity.TimeOfDayTemp
import com.itis.avitoproject.domain.entity.WeatherFullDay
import com.itis.avitoproject.presentation.ui.rv.TimeOfDayTempAdapter

class DayFragment : Fragment(R.layout.fragment_day) {
    private val binding by viewBinding(FragmentDayBinding::bind)

    private val tempsAdapter: TimeOfDayTempAdapter by lazy {
        TimeOfDayTempAdapter()
    }

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTemps.adapter = tempsAdapter
        openBundle()
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun openBundle() {
        val weatherData: WeatherFullDay
        if (arguments?.get("weatherData") != null) {
            weatherData = arguments?.get("weatherData") as WeatherFullDay
            setWeather(weatherData)
        } else {
            showMessage(getString(R.string.fail))
            requireActivity().onBackPressed()
        }
    }

    private fun setDayWeather(list: List<TimeOfDayTemp>) {
        tempsAdapter.submitList(list.toMutableList())
    }

    private fun setWeather(fullDay: WeatherFullDay) {
        with(binding) {
            setDayWeather(fullDay.listTimesOfDayTemp)
            tvTitle.text = "Погода на " + fullDay.date
            ltFullDetails.tvWindNumber.text = fullDay.windSpeed + " " + "м/c"
            ltFullDetails.tvDirectionPeak.text = fullDay.windDirection
            ltFullDetails.tvHumidityNumber.text = fullDay.humidity.toString() + "%"
            ltFullDetails.tvPressureNumber.text = fullDay.pressure.toString() + "гПа"

            ltFullDetails.tvMinTemp.text = fullDay.minTemp + " " + "°C"
            ltFullDetails.tvMaxTemp.text = fullDay.maxTemp + " " + "°C"
            tvDescription.text = fullDay.weatherDescription

            val weatherIcon = fullDay.weatherIcon
            val options = RequestOptions()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            glide.load(generateIcon(weatherIcon))
                .apply(options)
                .into(ivIcon)
        }
    }

    private fun generateIcon(iconId: String): String {
        val url = "http://openweathermap.org/img/wn/$iconId@2x.png"
        print(url)
        return url
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
