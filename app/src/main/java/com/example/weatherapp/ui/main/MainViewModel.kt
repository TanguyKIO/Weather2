package com.example.weatherapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val temp : LiveData<Int> = TODO()
    init{
        temp.value=20
    }

}