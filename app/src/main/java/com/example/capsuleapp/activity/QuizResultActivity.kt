package com.example.capsuleapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.capsuleapp.viewModel.CapsuleViewModel
import com.example.capsuleapp.R

class QuizResultActivity : AppCompatActivity() {
    lateinit var viewModel: CapsuleViewModel
    private val sharedPrefFile = "kotlinsharedpreference"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)
        viewModel = ViewModelProvider(this).get(CapsuleViewModel::class.java)
        val topicName=findViewById<TextView>(R.id.topic_)
        val timeConsume=findViewById<TextView>(R.id.tv_time_consume)

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val sharedIdValue = sharedPreferences.getString("id","00:00")
        val score:TextView=findViewById(R.id.tv_score)

        topicName.text=viewModel.capsule.Topic
        score.text="${sharedPreferences.getInt("score",0)}/${viewModel.capsule.quizQuestions.size}"
        timeConsume.text="${sharedIdValue} min"


        val restart=findViewById<AppCompatButton>(R.id.restart_capsule)
        restart.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}