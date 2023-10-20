package com.example.capsuleapp.dataclass

data class QuizQuestion(val question: String,
                        val answerChoices: List<String>,
                        val correctAnswerIndex: Int)
