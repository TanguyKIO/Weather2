package com.example.weatherapp.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.data.Weather
import com.example.weatherapp.data.WeatherType
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlin.random.Random

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
            viewModel.update(Random.nextInt(-3,42))
        }
        viewModel.weather.observe(viewLifecycleOwner, Observer { setWeather(it) })
    }


    private fun setWeather(weather: Weather) {
        when (weather.type) {
            WeatherType.SUN -> weatherIcon.setImageResource(R.drawable.sun)
            WeatherType.CLOUDY -> weatherIcon.setImageResource(R.drawable.weather)
        }
        weatherTemp.text=weather.temperature.toString()

    }
}
