package com.example.trainspace

import java.util.*

object Helper {
    fun formatTime(timeString: String): String {
        var hour = timeString.substring(0, 2).toInt()
        var half = "AM"
        if (hour >= 13) {
            hour -= 12
            half = "PM"
        } else if (hour == 0) {
            hour += 12
        }
        val minute = timeString.substring(2)

        return "$hour:$minute $half"
    }

    fun undoFormatTime(timeString: String): String {
        val time = timeString.split(":")
        val half = time[1].substring(2)
        val minute = time[1].substring(0, 2)
        var hour = time[0].toInt()
        if (half.toUpperCase(Locale.ROOT) == "PM" && hour < 12) {
            hour += 12
        } else if (half.toUpperCase(Locale.ROOT) == "AM" && hour < 12) {
            hour -= 12
        }
        var hourString = hour.toString()
        if (hour < 10) {
            hourString = "0$hourString"
        }

        return "$hourString$minute"
    }
}