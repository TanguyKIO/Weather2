package com.example.weatherapp.ui.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.entities.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val currentViewModel: CurrentWeatherViewModel by viewModels()
    private val forecastViewModel: ForecastWeatherViewModel by viewModels()

    private val weatherAdapter = WeatherAdapter()

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
        recyclerView = view.findViewById(R.id.recyclerView)

        val s = "<font color=#FF8C00>Weather</font><font color=#00000>App</font>"
        weatherTitle.text = Html.fromHtml(s)
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)

        refresh?.setOnRefreshListener {
            currentViewModel.update()
            forecastViewModel.update()
        }

        viewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = viewManager
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = weatherAdapter

        currentViewModel.weatherModel.observe(
            viewLifecycleOwner,
            Observer { setCurrentWeather(it) })
        currentViewModel.state.observe(viewLifecycleOwner, Observer { showCurrentState(it) })
        
        currentViewModel.wears.observe(viewLifecycleOwner,
            Observer { setRecommendations(it) })
        
        forecastViewModel.weatherModels.observe(
            viewLifecycleOwner,
            Observer { setForecastWeather(it) })
        forecastViewModel.state.observe(viewLifecycleOwner, Observer { showCurrentState(it) })
    }

    private fun setRecommendations(recommendations: List<Wears>) {
        for(wear in recommendations){
            when(wear){
                Wears.SUNGLASSES -> sunglasses.text = "Sunglasses"
                Wears.WINDBREAKER -> wind_breaker.text = "Windbreaker"
                Wears.SWEATER -> sweater.text = "Sweater"
                Wears.WINTER_JACKET -> winter_jacket.text = "WinterJacket"
            }
        }
    }

    private fun showCurrentState(state: State) {
        when (state) {
            State.NO_DATA -> {
                warning.text = "Aucune donnée accessible"
                refresh?.isRefreshing = false
            }
            State.FAILURE -> {
                warning.text = "Connectez-vous à Internet"
                refresh?.isRefreshing = false
            }
            State.LOADING -> {
                refresh?.isRefreshing = true
            }
            State.SUCCESS -> {
                warning.text = null
                refresh?.isRefreshing = false
            }
        }
    }

    private fun setCurrentWeather(weatherModel: WeatherModel?) {
        if (weatherModel != null) {
            weatherTemp.text = "${weatherModel.temp} °C"
            weatherDate.text = weatherModel.time
            weatherCity.text= weatherModel.city
        } else {
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
            WeatherType.UNKNOWN -> weatherIcon.setImageResource(R.drawable.nodata)
            null -> weatherIcon.setImageResource(R.drawable.nodata)
        }

    }


    private fun setForecastWeather(weatherModels: List<WeatherModel>) {
        weatherAdapter.weathers = weatherModels
        weatherAdapter.notifyDataSetChanged()
    }
}
