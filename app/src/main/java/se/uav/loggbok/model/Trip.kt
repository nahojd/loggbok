package se.uav.loggbok.model

import android.location.Location
import java.time.LocalDateTime

data class Trip(
        val startTime: LocalDateTime,
        var endTime: LocalDateTime?,
        val startPosition: Location,
        var endPosition: Location?) {

}