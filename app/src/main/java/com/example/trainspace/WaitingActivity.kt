package com.example.trainspace

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.activity_waiting.*

class WaitingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)
    }

    override fun onResume() {
        super.onResume()

        val animator = ObjectAnimator.ofFloat(loader, View.TRANSLATION_X, 1200f)
        animator.duration = 1000
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.doOnEnd {
            val i = Intent(this, ApproachingActivity::class.java)
            i.putExtras(intent)
            this.startActivity(i)
        }
        animator.start()
    }
}