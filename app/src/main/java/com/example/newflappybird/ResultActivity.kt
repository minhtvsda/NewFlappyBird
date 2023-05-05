package com.example.newflappybird

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.newflappybird.databinding.ActivityResultBinding
import kotlin.system.exitProcess

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        score = intent.getIntExtra("score", 0)
        binding.textViewMyScore.text = "Your score: $score"

        val sharePreferences = this.getPreferences(MODE_PRIVATE)
        val highestScore = sharePreferences?.getInt("highestScore",0)!!

        if (score >= 300){
            binding.textViewResultInfo.text = "You won the game!"
            binding.textViewHighhestScore.text = "Highest Score: $score"
            sharePreferences.edit().putInt("highestScore", score).apply()

        }   else if (score >= highestScore){
            binding.textViewMyScore.text = "Sorry, you lost the game."
            binding.textViewHighhestScore.text = "Highest Score: $score"
            sharePreferences.edit().putInt("highestScore", score).apply()

        }   else{
            binding.textViewMyScore.text = "Sorry, you lost the game."
            binding.textViewHighhestScore.text = "Highest Score: $highestScore"
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Flappy Bird")
        builder.setMessage("Are you sure you want to quit the game?")
            .setCancelable(false)
            .setNegativeButton("Quit Game") { p0, p1 ->
                moveTaskToBack(true)
                android.os.Process.killProcess(android.os.Process.myPid())
                exitProcess(/* status = */ 0)
            }

            .setPositiveButton("Cancel"){
                    dialog, p1 -> dialog.cancel()
            }

        builder.create().show()

    }

}