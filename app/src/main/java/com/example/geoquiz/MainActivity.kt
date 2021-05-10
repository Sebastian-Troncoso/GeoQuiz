package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.geoquiz.model.QuizViewModel

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private var score = 0

    // QuizViewModel persist the current data in memory during a device rotation. Note that
    // ViewModel should not be dependant on the main activity, or a memory leak could happen if
    // the main activity is destroyed an the view model tries to persist the sale data in dead
    // activity
    // lazy mean that VieModel the calculation and assignment will not happen until the first time
    // the access to the ViewModel occurs. This is a safeguard.
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        // Check if there is a currentIndex is saved in the instant state, otherwise sets to zero
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        previousButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        //questionTextView = findViewById(R.id.question_text_view)

        // Check if the answer is true
        trueButton.setOnClickListener { view: View ->
            trueButton.isClickable = false
            checkAnswer(true)
        }
        // Check if the answer is false
        falseButton.setOnClickListener { view: View ->
            falseButton.isClickable = false
            checkAnswer(false)
        }
        // Moves to the previous question
        previousButton.setOnClickListener { view: View ->
            quizViewModel.moveToPrevious()
            updateQuestion()
        }
        // Moves to the next question
        nextButton.setOnClickListener { view: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")

    }

    // Updates the text view
    private fun updateQuestion() {
        trueButton.isClickable = true
        falseButton.isClickable = true
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    // Checks if the user's answer is correct or not
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId: Int
        if (userAnswer == correctAnswer) {
            score++
            messageResId = R.string.correct_toast
        } else {
            messageResId = R.string.incorrect_toast
        }
        if (quizViewModel.checkLastQuestion()) {
            Handler().postDelayed({
                Toast.makeText(this, "You score: $score/6", Toast.LENGTH_SHORT).show()
                score = 0
            }, 1000)
        }
        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 200)
        toast.show()
    }

}



