package se.uav.loggbok

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import se.uav.loggbok.model.Trip
import se.uav.loggbok.model.TripRepositoryImpl
import se.uav.loggbok.util.put
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class TripRepositoryInstrumentedTest {

    @Test
    fun loadTrips_reads_trips_from_shared_preferences() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val prefs = appContext.getSharedPreferences("trips", Context.MODE_PRIVATE)

        val trips = listOf(
            Trip(LocalDateTime.now(), LocalDateTime.now(), "Test trip")
        )

        prefs.put("allTrips", trips)

        val repository = TripRepositoryImpl(appContext)

        val result = repository.loadTrips()
        assertEquals(1, result.size)
        assertEquals("Test trip", result[0].name)
    }
}