package com.example.capsuleapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.capsuleapp.dataclass.Capsule
import com.example.capsuleapp.dataclass.QuizQuestion

class CapsuleViewModel(application: Application) : AndroidViewModel(application) {
    val capsule: Capsule
    val url:String
    init{
        url="bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"

        val questionList = listOf(
            QuizQuestion("_____ and ___ are collectively known as twin characteristics of growth", listOf("Increase in mass, Cell differentiation","Increase in number of individuals, Celldifferentiation","Increase in mass, Increase in number of individuals","Cell differentiation, Cell organisation"),1),
            QuizQuestion("_____ and ___ are collectively known as twin characteristics of growth", listOf("Increase in mass, Cell differentiation","Increase in number of individuals, Celldifferentiation","Increase in mass, Increase in number of individuals","Cell differentiation, Cell organisation"),2),
            QuizQuestion("_____ and ___ are collectively known as twin characteristics of growth", listOf("Increase in mass, Cell differentiation","Increase in number of individuals, Celldifferentiation","Increase in mass, Increase in number of individuals","Cell differentiation, Cell organisation"),3),
            QuizQuestion("_____ and ___ are collectively known as twin characteristics of growth", listOf("Increase in mass, Cell differentiation","Increase in number of individuals, Celldifferentiation","Increase in mass, Increase in number of individuals","Cell differentiation, Cell organisation"),4)

        )
        //url="https://imgur.com/7bMqysJ"
     // Log.d("reached" ,"view model1234 ${URL}")
        var textContent:String="Blood is a special fluid that is actually a connective tissue. We can call it a transport liquid which is pumped by the heart to different parts of the body, after which it comes back again to the heart. This is a process that happens continuously in your body, till your heart beats. The cells of the body are highly susceptible and they need a constant supply of blood. If its flow stops, death can occur within minutes."


        capsule = Capsule("Blood",url,textContent,questionList)
        //capsule.videoUrl="bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"

        Log.d("reached" ,"view model ${capsule.videoUrl}")

    }

}



