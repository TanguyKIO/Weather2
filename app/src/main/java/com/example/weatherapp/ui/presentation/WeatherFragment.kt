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
import com.example.weatherapp.domain.entities.WeatherType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    companion object {
        fun newInstance() = WeatherFragment()
    }


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
            Toast.makeText(
                activity?.applicationContext,
                "Pas connecté à Internet",
                Toast.LENGTH_SHORT
            ).show()
        }
        when (weatherResponse.weatherModel?.weather) {
            WeatherType.THUNDERSTORM -> weatherIcon.setImageResource(R.drawable.thunder)
            WeatherType.DRIZZLE -> weatherIcon.setImageResource(R.drawable.rainy)
            WeatherType.RAIN -> weatherIcon.setImageResource(R.drawable.rainy)
            WeatherType.SNOW -> weatherIcon.setImageResource(R.drawable.snowy)
            WeatherType.FOG -> weatherIcon.setImageResource(R.drawable.fog)
            WeatherType.CLEAR -> weatherIcon.setImageResource(R.drawable.clear)
            WeatherType.CLOUDS -> weatherIcon.setImageResource(R.drawable.cloudy)
            else -> weatherIcon.setImageResource(R.drawable.nowifi)
        }
        if (weatherResponse.weatherModel?.weather != WeatherType.UNKNOWN) {
            weatherTemp.text = "${weatherResponse.weatherModel?.temp} °C"
        }else {
            weatherTemp.text = null
        }
    }
}
