package com.example.coroutinesflow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var running = true

    var isRestart = false

    private var activeJob: Job? = null

    val counterFlowPI = MutableStateFlow("3.14")

    var count = 0

    fun startPI() {
        running = true
        if (activeJob?.isActive == true) {
            stopPI()
        }
        activeJob = viewModelScope.launch(Dispatchers.Default) {
            while (running) {
                counterFlowPI.value = "3.14${count++}"
                delay(100)
            }
        }
    }

    fun stopPI() {
        running = false
        if (!running) {
            activeJob?.cancel()
            activeJob = null
        }
    }

}