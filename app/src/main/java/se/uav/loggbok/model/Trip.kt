package se.uav.loggbok.model

import java.time.LocalDateTime

data class Trip(
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val name: String
)