package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton:ImageButton
    private lateinit var questionTextView: TextView

    // Creates a list of questions
    private val questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true))

    private var currentIndex = 0

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        previousButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

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
        previousButton.setOnClickListener{ view: View ->
            currentIndex =  (currentIndex -1) % questionBank.size
            if ( currentIndex == -1)
                currentIndex = questionBank.size - 1
            updateUI()
        }
        // Moves to the next question
        questionTextView.setOnClickListener{ view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateUI()
        }
        // Moves to the next question
        nextButton.setOnClickListener { view : View ->
            // sets the current index from 0 to 5
            currentIndex = (currentIndex + 1) % questionBank.size
            updateUI()
        }
        updateUI()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")

    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")

    }

    // Updates the text view
    private fun updateUI() {
        trueButton.isClickable = true
        falseButton.isClickable = true
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    // Checks if the user's answer is correct or not
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId: Int
            if(userAnswer == correctAnswer) {
                score++
                messageResId = R.string.correct_toast
            } else {
               messageResId =  R.string.incorrect_toast
            }


        // score is still having issues
        if (currentIndex == questionBank.size - 1){
            Handler().postDelayed({
                Toast.makeText(this, "You score: $score/6", Toast.LENGTH_SHORT).show()
                score = 0
            }, 1000)
        }


        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP,0,200)
        toast.show()


    }



}



