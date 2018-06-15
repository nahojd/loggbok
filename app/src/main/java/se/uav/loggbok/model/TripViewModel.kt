package se.uav.loggbok.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.SystemClock
import android.util.Log
import java.time.LocalDateTime
import java.util.*

class TripViewModel : ViewModel() {
    private val TAG = this::class.java.name

    private val _elapsedTime = MutableLiveData<Long>()
    private val _trips = MutableLiveData<List<Trip>>()
    private var _initialTime: Long = 0
    private var timer = Timer()

    init {
        _trips.postValue(listOf())
        _elapsedTime.postValue(0)
    }

    fun start() {
       _initialTime = SystemClock.elapsedRealtime()
       timer = Timer()

       timer.scheduleAtFixedRate(TimerTicker(_initialTime, _elapsedTime), 1000, 1000)
    }

    fun stop() {
        timer.cancel()

        if (trips.value == null || elapsedTime.value == null)
            return

        val endTime = LocalDateTime.now()
        val startTime = endTime.minusSeconds(_elapsedTime.value!!)
        val trip = Trip(startTime, endTime, "${startTime.dayOfWeek.name} ${Math.floor(Math.random() * 1000)}")
        val trips = _trips.value!! + trip
        _trips.postValue(trips)

        Log.d(TAG, "Added trip ${trip.name}. Trips now contains ${trips.size} elements.")
    }

    private class TimerTicker(val initialTime : Long, val elapsedTime: MutableLiveData<Long>) : TimerTask() {
        override fun run() {
            val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
            elapsedTime.postValue(newValue)
        }
    }

    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    val trips: LiveData<List<Trip>>
        get() = _trips
}