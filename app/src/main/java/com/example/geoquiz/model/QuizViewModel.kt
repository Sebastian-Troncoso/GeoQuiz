package com.example.geoquiz.model

import androidx.lifecycle.ViewModel
import com.example.geoquiz.R

private const val TAG = "QuizViewModel"

/**
 * QuizViewModel is a child of ViewModel. ViewModel allows to persist current data in memory during
 * device rotations. Any changes to the main activity, ViewModel will persist the new information.
 * Memory leak will happen if ViewModel tries to persist data from a destroyed main activity.
 */
class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var isCheater = false

    // Creates a list of questions
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = (currentIndex - 1) % questionBank.size
        if (currentIndex == -1)
            currentIndex = questionBank.size - 1
    }

    fun checkLastQuestion(): Boolean {
        return currentIndex == questionBank.size - 1
    }
}