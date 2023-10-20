package com.example.capsuleapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.capsuleapp.viewModel.CapsuleViewModel
import com.example.capsuleapp.R
import com.example.capsuleapp.adapter.ViewPagerAdapter
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(){
    lateinit var viewModel: CapsuleViewModel
    private lateinit var countdownText: TextView
    private var timeConsume:String=""
    private lateinit var countDownTimer: CountDownTimer
    private val totalTimeInMillis: Long = 10 * 60 * 1000
    private val sharedPrefFile = "kotlinsharedpreference"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager:ViewPager2 = findViewById(R.id.view_pager)
        val topic=findViewById<TextView>(R.id.topic)
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(CapsuleViewModel::class.java)
        topic.text=viewModel.capsule.Topic

            viewPager.adapter= ViewPagerAdapter(this)
        countdownText = findViewById(R.id.countdownText)

        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(minutes)
                timeConsume = String.format("%02d:%02d", 9-minutes,seconds )
                val timeLeft = String.format("%02d:%02d", minutes, seconds)
                countdownText.text = "$timeLeft min"
            }

            override fun onFinish() {
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("id","10:00")
                editor.putInt("score",0)
                editor.apply()
                editor.commit()
                countdownText.text = "Time Left: 00:00"

                val intent= Intent(this@MainActivity, QuizResultActivity::class.java)
                startActivity(intent)

            }
        }
        countDownTimer.start()

    }
    override fun onPause(){
        super.onPause()
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("id",timeConsume)
        editor.apply()
        editor.commit()
       

    }


    }



