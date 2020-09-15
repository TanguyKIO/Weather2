package com.example.weatherapp.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.domain.entities.WeatherResponse
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.refresh).setOnClickListener {
            viewModel.update()
        }
        viewModel.weatherModel.observe(viewLifecycleOwner, Observer { setWeather(it) })
    }


    private fun setWeather(weatherResponse: WeatherResponse) {
        if (!weatherResponse.isSuccess) {
            Toast.makeText(activity?.applicationContext, "Pas connecté à Internet", Toast.LENGTH_SHORT).show()
        }
        when (weatherResponse.weatherData?.weather) {
            in 200..232 -> weatherIcon.setImageResource(R.drawable.thunder)
            in 300..321 -> weatherIcon.setImageResource(R.drawable.rainy)
            in 500..504 -> weatherIcon.setImageResource(R.drawable.rainy)
            in 511..531 -> weatherIcon.setImageResource(R.drawable.rainy)
            in 600..622 -> weatherIcon.setImageResource(R.drawable.snowy)
            in 701..781 -> weatherIcon.setImageResource(R.drawable.fog)
            800 -> weatherIcon.setImageResource(R.drawable.clear)
            801 -> weatherIcon.setImageResource(R.drawable.sunny)
            in 802..804 -> weatherIcon.setImageResource(R.drawable.cloudy)
            else -> weatherIcon.setImageResource(R.drawable.nowifi)
        }
        if (weatherResponse.weatherData != null) {
            weatherTemp.text = "${weatherResponse?.weatherData?.temp} °C"
        } else {
            weatherTemp.text = null
        }
    }
}
