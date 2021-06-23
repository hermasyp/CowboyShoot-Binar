package com.catnip.cowboyshoot.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.catnip.cowboyshoot.R
import com.catnip.cowboyshoot.ui.intro.AppIntroActivity

class SplashScreenActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        loadImageSplash()
        setEventSplash()
    }

    private fun loadImageSplash(){
        //todo : load image splash
    }

    override fun onDestroy() {
        super.onDestroy()
        //destroy the timer for case new activity still opened when splashscreen destoryed
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }


    private fun setEventSplash() {
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, AppIntroActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        timer?.start()
    }

}