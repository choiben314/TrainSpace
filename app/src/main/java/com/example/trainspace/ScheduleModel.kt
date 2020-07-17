package com.example.trainspace
object ScheduleModel {
    data class Result(
        val trips: List<ResultItem>
    )
    data class ResultItem (val originTime: String,
                           val destinationTime: String,
                           val branch: String,
                           val duration: Int,
                           val capacity: Int,
                           val status: String,
                           val trainName:  String,
                           val assignedCar: Int,
                           val assignedCarArea: String,
                           val assignedCarIndex: Int,
                           val numCars: Int)
}
