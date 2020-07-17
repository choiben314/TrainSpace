package com.example.trainspace

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.activity_boarding.*
import kotlinx.android.synthetic.main.activity_waiting.*

class BoardingActivity : AppCompatActivity() {
    private var shortAnimationDuration: Int = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boarding)
    }

    override fun onResume() {
        super.onResume()
        number_arrows.visibility = View.INVISIBLE

        val carNumber = intent.extras!!.getString("EXTRA_CAR_NUMBER")
        car_number_1.text = carNumber
        car_number_2.text = carNumber

        val animator = ObjectAnimator.ofFloat(car_view, View.TRANSLATION_X, 0f)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.doOnEnd {
            number_arrows.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration.toLong())
                    .setListener(null)
            }
        }
        animator.start()
    }
}