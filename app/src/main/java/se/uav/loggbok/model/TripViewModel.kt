package se.uav.loggbok.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.SystemClock
import java.util.*

class TripViewModel : ViewModel() {
    private val _elapsedTime = MutableLiveData<Long>()
    private var _initialTime: Long = 0
    private var timer = Timer()

    fun start() {
       _initialTime = SystemClock.elapsedRealtime()
       timer = Timer()

       timer.scheduleAtFixedRate(TimerTicker(_initialTime, _elapsedTime), 1000, 1000)
    }

    fun stop() {
        timer.cancel()
    }

    private class TimerTicker(val initialTime : Long, val elapsedTime: MutableLiveData<Long>) : TimerTask() {
        override fun run() {
            val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
            elapsedTime.postValue(newValue)
        }
    }

    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

}