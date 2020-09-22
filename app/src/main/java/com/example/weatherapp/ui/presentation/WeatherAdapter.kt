package com.example.weatherapp.ui.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherType

class WeatherAdapter(var weathers: List<WeatherModel>? = emptyList()) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.WeatherHolder {
        return WeatherHolder(parent.inflate(R.layout.list_layout, false))
    }

    override fun getItemCount(): Int{
        return if(weathers == null){
            0
        } else weathers!!.size
    }

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherHolder, position: Int) {
        holder.bind(position)
    }

    inner class WeatherHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {


        private var date: TextView = view.findViewById(R.id.forecastDate)
        private var temp: TextView = view.findViewById(R.id.forecastTemp)
        private var icon: ImageView = view.findViewById(R.id.forecastIcon)

        internal fun bind(position: Int){
            date.text = weathers?.get(position)?.time
            temp.text = "${weathers?.get(position)?.temp} °C"
            when (weathers?.get(position)?.weather) {
                WeatherType.THUNDERSTORM -> icon.setImageResource(R.drawable.thunder)
                WeatherType.DRIZZLE -> icon.setImageResource(R.drawable.rainy)
                WeatherType.RAIN -> icon.setImageResource(R.drawable.rainy)
                WeatherType.SNOW -> icon.setImageResource(R.drawable.snowy)
                WeatherType.FOG -> icon.setImageResource(R.drawable.fog)
                WeatherType.CLEAR -> icon.setImageResource(R.drawable.clear)
                WeatherType.CLOUDS -> icon.setImageResource(R.drawable.cloudy)
                null -> icon.visibility = View.GONE
            }
        }

    }

}

