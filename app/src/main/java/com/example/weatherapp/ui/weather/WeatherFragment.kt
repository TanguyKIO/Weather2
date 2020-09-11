package com.example.weatherapp.ui.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.data.WeatherModel
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var appContext: Context

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appContext = requireActivity().applicationContext
        viewModel = WeatherViewModel(appContext)
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.refresh).setOnClickListener {
            viewModel.update()
        }
        viewModel.weatherModel.observe(viewLifecycleOwner, Observer { setWeather(it) })
    }


    private fun setWeather(weatherModel: WeatherModel?) {
        if (weatherModel == null) {
            weatherIcon.setImageResource(R.drawable.nowifi)
            weatherTemp.text = "Pas d'internet"
        } else {
            when (weatherModel.weather) {
                in 200..232 -> weatherIcon.setImageResource(R.drawable.thunder)
                in 300..321 -> weatherIcon.setImageResource(R.drawable.rainy)
                in 500..504 -> weatherIcon.setImageResource(R.drawable.rainy)
                in 511..531 -> weatherIcon.setImageResource(R.drawable.rainy)
                in 600..622 -> weatherIcon.setImageResource(R.drawable.snowy)
                in 701..781 -> weatherIcon.setImageResource(R.drawable.fog)
                800 -> weatherIcon.setImageResource(R.drawable.clear)
                801 -> weatherIcon.setImageResource(R.drawable.sunny)
                in 802..804 -> weatherIcon.setImageResource(R.drawable.cloudy)
            }
            weatherTemp.text = "${weatherModel?.temp.toString()} °C"
        }
    }
}
