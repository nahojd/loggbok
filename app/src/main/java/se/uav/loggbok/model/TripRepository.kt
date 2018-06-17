package se.uav.loggbok.model

import android.content.Context
import se.uav.loggbok.util.get
import se.uav.loggbok.util.put

interface TripRepository {
    fun loadTrips() : List<Trip>
    fun saveTrips(trips: List<Trip>)
}

class TripRepositoryImpl(private val context: Context) : TripRepository {

    override fun loadTrips() : List<Trip> = prefs.get("allTrips", listOf())

    override fun saveTrips(trips: List<Trip>) {
        prefs.put("allTrips", trips)
    }

    private val prefs
        get() = context.getSharedPreferences("trips", Context.MODE_PRIVATE)
}