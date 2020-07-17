package com.example.trainspace

import retrofit2.http.GET
import io.reactivex.rxjava3.core.Observable
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

interface ScheduleService {

    @GET("trips/{origin}/{destination}/{time}")
    fun setTripURL(
        @Path("origin") origin: String,
        @Path("destination") destination: String,
        @Path("time") time: String
    ): Observable<ScheduleModel.Result>


    companion object {
        fun create():ScheduleService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://52.149.208.51/")
                .build()

            return retrofit.create(ScheduleService::class.java)
        }
    }
}