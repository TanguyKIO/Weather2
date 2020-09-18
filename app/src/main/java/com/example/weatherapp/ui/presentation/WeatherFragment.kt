package com.example.weatherapp.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.domain.entities.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var progressBar: ProgressBar? = null
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
        progressBar = view.findViewById(R.id.loadingSpinner) as ProgressBar
        viewModel.weatherModel.observe(viewLifecycleOwner, Observer { setWeather(it) })
        viewModel.state.observe(viewLifecycleOwner, Observer { showState(it) })
    }

    private fun showState(state: State){
        when(state){
            State.NO_DATA -> {
                warning.text = "Aucune donnée en cache, connectez-vous à Internet"
                progressBar?.visibility = GONE
            }
            State.FAILURE -> {
                warning.text = "Connectez-vous à Internet"
                progressBar?.visibility = GONE
            }
            State.LOADING -> {
                warning.text = null
                progressBar?.visibility = VISIBLE
            }
            State.SUCCESS -> {
                warning.text = null
                progressBar?.visibility = GONE
            }
        }
    }

    private fun setWeather(weatherModel: WeatherModel?) {
        if(weatherModel != null){
            weatherTemp.text = "${weatherModel.temp} °C"
            weatherDate.text = weatherModel.time
        } else{
            weatherTemp.text = null
        }
        when (weatherModel?.weather) {
            WeatherType.THUNDERSTORM -> weatherIcon.setImageResource(R.drawable.thunder)
            WeatherType.DRIZZLE -> weatherIcon.setImageResource(R.drawable.rainy)
            WeatherType.RAIN -> weatherIcon.setImageResource(R.drawable.rainy)
            WeatherType.SNOW -> weatherIcon.setImageResource(R.drawable.snowy)
            WeatherType.FOG -> weatherIcon.setImageResource(R.drawable.fog)
            WeatherType.CLEAR -> weatherIcon.setImageResource(R.drawable.clear)
            WeatherType.CLOUDS -> weatherIcon.setImageResource(R.drawable.cloudy)
            null -> weatherIcon.setImageResource(R.drawable.nowifi)
        }

    }
}
