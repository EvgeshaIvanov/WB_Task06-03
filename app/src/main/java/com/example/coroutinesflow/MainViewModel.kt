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

                counterFlowPI.value = piSpigot(count++)
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

    private fun piSpigot(n: Int): String {

        val pi = StringBuilder(n)
        val boxes = n * 10 / 3
        val reminders = IntArray(boxes)

        for (i in 0 until boxes) {
            reminders[i] = 2
        }
        var heldDigits = 0
        for (i in 0 until n) {
            var carriedOver = 0
            var sum = 0
            for (j in boxes - 1 downTo 0) {
                reminders[j] *= 10
                sum = reminders[j] + carriedOver
                val quotient = sum / (j * 2 + 1)
                reminders[j] = sum % (j * 2 + 1)
                carriedOver = quotient * j
            }
            reminders[0] = sum % 10
            var q = sum / 10

            if (q == 9) {
                heldDigits++
            } else if (q == 10) {
                q = 0
                for (k in 1..heldDigits) {
                    var replaced = pi.substring(i - k, i - k + 1).toInt()
                    if (replaced == 9) {
                        replaced = 0
                    } else {
                        replaced++
                    }
                    pi.deleteCharAt(i - k)
                    pi.insert(i - k, replaced)
                }
                heldDigits = 1
            } else {
                heldDigits = 1
            }
            pi.append(q)
        }
        if (pi.length >= 2) {
            pi.insert(1, '.')
        }

        return pi.toString()
    }
}