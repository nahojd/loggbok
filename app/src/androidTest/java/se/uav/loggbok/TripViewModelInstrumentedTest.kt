package se.uav.loggbok

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import kotlinx.coroutines.experimental.yield

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import se.uav.loggbok.model.Trip
import se.uav.loggbok.model.TripRepositoryImpl
import se.uav.loggbok.model.TripViewModel
import se.uav.loggbok.util.get


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TripViewModelInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("se.uav.loggbok", appContext.packageName)
    }

    @Test
    fun stop_saves_trips_to_shared_preferences() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val prefs = appContext.getSharedPreferences("trips", Context.MODE_PRIVATE)

        val repository = TripRepositoryImpl(appContext)
        val viewModel = TripViewModel(repository)

        //TODO: Mock the Clock so that we can test time elapsed as well
        viewModel.start()
        viewModel.stop()

        val data = prefs.get<List<Trip>>("allTrips", listOf())
        assertEquals(1, data.size)
    }
}
