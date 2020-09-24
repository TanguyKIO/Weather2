package com.example.weatherapp.ui.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.entities.Wears
import com.example.weatherapp.domain.entities.WeatherAndRecommendation
import com.example.weatherapp.domain.entities.WeatherType
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlin.math.roundToInt

class WeatherAdapter(var weathers: List<WeatherAndRecommendation> = emptyList()) :
    RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherAdapter.WeatherHolder {
        return WeatherHolder(parent.inflate(R.layout.list_layout, false))
    }

    override fun getItemCount(): Int = weathers.size


    override fun onBindViewHolder(holder: WeatherAdapter.WeatherHolder, position: Int) {
        holder.bind(position)
    }

    inner class WeatherHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {


        private var date: TextView = view.findViewById(R.id.forecastDate)
        private var temp: TextView = view.findViewById(R.id.forecastTemp)
        private var icon: ImageView = view.findViewById(R.id.forecastIcon)
        private var sunglasses: ImageView = view.findViewById(R.id.f_sunglasses)
        private var winterJacket: ImageView = view.findViewById(R.id.f_winter_jacket)
        private var sweater: ImageView = view.findViewById(R.id.f_sweater)
        private var windbreaker: ImageView = view.findViewById(R.id.f_windbreaker)

        init{
            sunglasses.setImageResource(R.drawable.sunglasses)
            winterJacket.setImageResource(R.drawable.jacket)
            windbreaker.setImageResource(R.drawable.windbreaker)
            sweater.setImageResource(R.drawable.sweater)
        }



        internal fun bind(position: Int) {
            date.text = weathers[position].weatherModel.time
            temp.text = "${weathers[position].weatherModel.temp.roundToInt()} °C"
            when (weathers[position].weatherModel.weather) {
                WeatherType.THUNDERSTORM -> icon.setImageResource(R.drawable.thunder)
                WeatherType.DRIZZLE -> icon.setImageResource(R.drawable.rainy)
                WeatherType.RAIN -> icon.setImageResource(R.drawable.rainy)
                WeatherType.SNOW -> icon.setImageResource(R.drawable.snowy)
                WeatherType.FOG -> icon.setImageResource(R.drawable.fog)
                WeatherType.CLEAR -> icon.setImageResource(R.drawable.clear)
                WeatherType.CLOUDS -> icon.setImageResource(R.drawable.cloudy)
                WeatherType.UNKNOWN -> icon.visibility = View.GONE
            }

            sunglasses.visibility = View.INVISIBLE
            windbreaker.visibility = View.INVISIBLE
            sweater.visibility = View.INVISIBLE
            winterJacket.visibility = View.INVISIBLE

            for (wear in weathers[position].wears)
                when (wear) {
                    Wears.SUNGLASSES -> sunglasses.visibility = View.VISIBLE
                    Wears.WINDBREAKER -> windbreaker.visibility = View.VISIBLE
                    Wears.SWEATER -> sweater.visibility = View.VISIBLE
                    Wears.WINTER_JACKET -> winterJacket.visibility = View.VISIBLE
                }
        }
    }
}

