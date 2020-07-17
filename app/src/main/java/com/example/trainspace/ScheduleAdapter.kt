package com.example.trainspace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.schedule_list_item.view.*

class ScheduleAdapter(private val context: Context,
                      private val fromLocation: String,
                      private val toLocation: String,
                      private val time: String,
                      private val listCarNumber: ArrayList<String>,
                      private val listDepartureTime: ArrayList<String>,
                      private val listArrivalTime: ArrayList<String>,
                      private val listStatus: ArrayList<String>,
                      private val listTrainLine: ArrayList<String>,
                      private val listCapacity: ArrayList<String>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return listDepartureTime.size
    }

    override fun getItem(position: Int): Any {
        return listDepartureTime[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.schedule_list_item, parent, false)

        rowView.list_item_departure_time.text = Helper.formatTime(listDepartureTime[position])
        rowView.list_item_arrival_time.text = Helper.formatTime(listArrivalTime[position])
        rowView.list_item_status.text = listStatus[position]
        rowView.list_item_train_line.text = listTrainLine[position]
        rowView.list_item_capacity.text = listCapacity[position] + context.getString(R.string.capacity)

        val cap_int = listCapacity[position].toInt()
        if (cap_int < 40) {
            rowView.capacity_indicator_circle.setBackgroundResource(R.drawable.green_circle)
        } else if (cap_int < 70) {
            rowView.capacity_indicator_circle.setBackgroundResource(R.drawable.yellow_circle)
        } else {
            rowView.capacity_indicator_circle.setBackgroundResource(R.drawable.red_circle)
        }

        //rowView.capacity_indicator_circle.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent))
        //Picasso.get().load(listImages[position]).placeholder(R.drawable.directions_railway_24px).into(rowView.list_item_image)
        //rowView.list_item_image.setImageResource(listImages[position])
        rowView.list_item_layout.setOnClickListener {

            // set listener to start activity and provide info on specific train
//            val i = Intent(Intent.ACTION_VIEW)
//            i.setData(Uri.parse(listLinks[position]))
//            context.startActivity(i)
            val extras = Bundle()
            extras.putString("EXTRA_FROM_LOCATION", fromLocation)
            extras.putString("EXTRA_TO_LOCATION", toLocation)
            extras.putString("EXTRA_TIME", time)
            extras.putString("EXTRA_CAR_NUMBER", listCarNumber[position])
            extras.putString("EXTRA_DEPARTURE_TIME", listDepartureTime[position])
            extras.putString("EXTRA_ARRIVAL_TIME", listArrivalTime[position])
            extras.putString("EXTRA_STATUS", listStatus[position])
            extras.putString("EXTRA_TRAIN_LINE", listTrainLine[position])
            extras.putString("EXTRA_CAPACITY", listCapacity[position])

            val i = Intent(context, WaitingActivity::class.java)
            i.putExtras(extras)

            context.startActivity(i)
        }
        return rowView
    }
}