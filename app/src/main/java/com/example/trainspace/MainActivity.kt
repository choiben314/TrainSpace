package com.example.trainspace

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var listCarNumber = arrayListOf<String>()
    private var listDepartureTime = arrayListOf<String>()
    private var listArrivalTime = arrayListOf<String>()
    private var listStatus = arrayListOf<String>()
    private var listTrainLine = arrayListOf<String>()
    private var listCapacity = arrayListOf<String>()

    private var disposable: Disposable? = null
    private val scheduleServe by lazy {
        ScheduleService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        toggleView()

        fun EditText.onSubmit(func: () -> Unit) {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    func()
                }
                true
            }
        }

        timeEditText.onSubmit { timeEditText.clearFocus() }

        timeEditText.setOnFocusChangeListener{ _, hasFocus ->
            if (!hasFocus) {
                val fromLocation = fromEditText.text.toString()
                val toLocation = toEditText.text.toString()
                val time = timeEditText.text.toString()
                val carNumber = ""

                if (fromLocation.isNotEmpty() && toLocation.isNotEmpty() && time.isNotEmpty()) {
                    listCarNumber = arrayListOf()
                    listDepartureTime = arrayListOf()
                    listArrivalTime = arrayListOf()
                    listStatus = arrayListOf()
                    listTrainLine = arrayListOf()
                    listCapacity = arrayListOf()
                    val adapter = ScheduleAdapter(
                        this@MainActivity,
                        fromLocation,
                        toLocation,
                        time,
                        listCarNumber,
                        listDepartureTime,
                        listArrivalTime,
                        listStatus,
                        listTrainLine,
                        listCapacity
                    )
                    scheduleListView.adapter = adapter
                    loadData(adapter)
                } else {
                    toggleView()
                }
            }
        }
    }

    private fun toggleView() {
        if (listDepartureTime.size > 0) {
            noTrainsView.visibility = View.GONE
            scheduleListView.visibility = View.VISIBLE
        } else {
            noTrainsView.visibility = View.VISIBLE
            scheduleListView.visibility = View.GONE
        }
    }

    private fun loadData(adapter: ScheduleAdapter) {
        disposable = scheduleServe.setTripURL(fromEditText.text.toString(), toEditText.text.toString(), Helper.undoFormatTime(timeEditText.text.toString()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val numTrips = result.trips.size
                    for (i in 0 until numTrips) {
                        listCarNumber.add(result.trips[i].assignedCar.toString())
                        listDepartureTime.add(result.trips[i].originTime)
                        listArrivalTime.add(result.trips[i].destinationTime)
                        listStatus.add(result.trips[i].status)
                        listTrainLine.add(result.trips[i].branch)
                        listCapacity.add(result.trips[i].capacity.toString())
                    }
                    toggleView()
                    adapter.notifyDataSetChanged()
                },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }
}