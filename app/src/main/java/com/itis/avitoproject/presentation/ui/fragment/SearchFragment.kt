package com.itis.avitoproject.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.itis.avitoproject.R
import com.itis.avitoproject.databinding.FragmentMainBinding
import com.itis.avitoproject.databinding.FragmentSearchBinding
import com.itis.avitoproject.presentation.viewmodel.MainFragmentViewModel
import com.itis.avitoproject.presentation.viewmodel.SearchFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val searchViewModel: SearchFragmentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        val message = arguments?.getString("message")
        with(binding) {

            if (message != null) tvSearch.text = message

            btnSearch.setOnClickListener {
                val name = etSearch.text.toString()
                if (name.isEmpty()) tvSearch.text = getString(R.string.add_city)
                else {
                    searchViewModel.getCurrentWeatherByName(name)
                    pbLoading.visibility = View.VISIBLE
                    btnSearch.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun initObservers() {
        searchViewModel.currentWeather.observe(viewLifecycleOwner) { weatherInfo ->
            weatherInfo.fold(onSuccess = {
                val bundle = bundleOf("weatherData" to it)
                searchViewModel.saveCityName(it.cityName)
                findNavController().navigate(
                    R.id.action_searchFragment_to_currentWeatherFragment,
                    bundle
                )
            }, onFailure = {
                with(binding) {
                    pbLoading.visibility = View.GONE
                    btnSearch.visibility = View.VISIBLE
                }
                showMessage(getString(R.string.fail))
                binding.tvSearch.text = "Город не найден, попробуйте снова"
            })
        }
        searchViewModel.error.observe(viewLifecycleOwner) {
            Timber.e(it)
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
