package com.supersonic.heartrate.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val currentDateTime: LocalDateTime = LocalDateTime.now()
fun getCurrentTime(): String{
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return currentDateTime.format(timeFormatter)
}
fun getCurrentDate(): String{
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return currentDateTime.format(dateFormatter)
}