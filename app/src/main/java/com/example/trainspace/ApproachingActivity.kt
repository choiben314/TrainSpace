package com.example.trainspace

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.activity_approaching.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_waiting.*
import kotlinx.android.synthetic.main.schedule_list_item.view.*

class ApproachingActivity : AppCompatActivity() {
    private var back = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approaching)

        // Included below for demo purposes.
        ticketButton.setOnClickListener {
            back = false
            val carNumber: String? = intent.extras!!.getString("EXTRA_CAR_NUMBER")
            val i = Intent(this, BoardingActivity::class.java)
            i.putExtra("EXTRA_CAR_NUMBER", carNumber)
            this.startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()

        back = true
        from.text = intent.extras!!.getString("EXTRA_FROM_LOCATION")
        to.text = intent.extras!!.getString("EXTRA_TO_LOCATION")
        car_annotation.text = "Car " + intent.extras!!.getString("EXTRA_CAR_NUMBER")
        wait_car_instructions.text = "Be in line for Car " + intent.extras!!.getString("EXTRA_CAR_NUMBER") + " when it arrives. Stand at least 6 feet from other bystanders."
        departure_time.text = Helper.formatTime(intent.extras!!.getString("EXTRA_DEPARTURE_TIME")!!)
        arrival_time.text = Helper.formatTime(intent.extras!!.getString("EXTRA_ARRIVAL_TIME")!!)
        status.text = intent.extras!!.getString("EXTRA_STATUS")
        train_line.text = intent.extras!!.getString("EXTRA_TRAIN_LINE")
        val capacity_text = intent.extras!!.getString("EXTRA_CAPACITY")
        capacity.text = capacity_text + this.getString(R.string.capacity)

        val cap_int = capacity_text!!.toInt()
        if (cap_int < 40) {
           capacity_indicator_circle.setBackgroundResource(R.drawable.green_circle)
        } else if (cap_int < 70) {
            capacity_indicator_circle.setBackgroundResource(R.drawable.yellow_circle)
        } else {
            capacity_indicator_circle.setBackgroundResource(R.drawable.red_circle)
        }

        val animator = ObjectAnimator.ofFloat(train_and_annotation, View.TRANSLATION_Y, 100f)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
//        animator.doOnEnd {
//            val i = Intent(this, ApproachingActivity::class.java)
//            this.startActivity(i)
//        }
        animator.start()
    }

    override fun onPause() {
        super.onPause()
        if (back) {
            val i = Intent(this, MainActivity::class.java)
            this.startActivity(i)
        }
    }
}