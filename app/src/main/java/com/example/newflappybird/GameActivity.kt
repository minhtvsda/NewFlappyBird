package com.example.newflappybird

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.newflappybird.databinding.ActivityGameBinding
import kotlin.math.floor

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private var touchControl = false
    private var beginControl = false

    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var runnable2: Runnable
    private lateinit var  handler2 : Handler
    private var life = 3
    private var score = 0

    //postion
    private var birdX = 0 ; private var  enemy1X = 0; private var  enemy2X = 0
    private var  enemy3X = 0
    private var  coin1X = 0
    private var  coin2X = 0
    private var birdY = 0
    private var  enemy1Y = 0; private var  enemy2Y = 0
    private var  enemy3Y = 0
    private var  coin1Y = 0
    private var  coin2Y = 0

    //dimension
    private var screenWith = 0
    private var screenHeight = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.constraintLayout.setOnTouchListener { p0, p1 ->
            binding.textViewStartInfo.visibility = INVISIBLE

            if (!beginControl) {
                beginControl = true
                screenWith = binding.constraintLayout.width
                screenHeight = binding.constraintLayout.height

                birdX = binding.bird.x.toInt()
                birdY = binding.bird.y.toInt()

                handler = Handler()
                runnable = Runnable {
                    moveToBird()
                    enemyControl()
                    collisionControl()
                }
                handler.post(runnable)

            } else {
                if (p1?.action == MotionEvent.ACTION_DOWN) {//mean touch screen operation is currently active
                    touchControl = true

                }
                if (p1?.action == MotionEvent.ACTION_UP) {//mean touch screen is currently finished
                    touchControl = false
                }

            }

            true
        }
    }


    private fun moveToBird(){
        if (touchControl){
            birdY -= screenHeight / 50

        }
        else{
            birdY += screenHeight / 50
        }
        val offYTop = (screenHeight - binding.bird.height)

        birdY = if (birdY <= 0) 0 else if (birdY >= offYTop) offYTop else birdY

        binding.bird.y = birdY.toFloat()
    }

    private fun enemyControl(){
        binding.enemy1.visibility = VISIBLE
        binding.enemy2.visibility = VISIBLE
        binding.enemy3.visibility = VISIBLE
        binding.coin1.visibility = VISIBLE
        binding.coin2.visibility = VISIBLE

        enemy1X -= screenWith / if (score in 50..99) 130 else if (score in 100 .. 199) 110
            else if (score >= 200) 90 else 150

        if (enemy1X < 0 ) {
            enemy1X = screenWith + 200
            enemy1Y = floor(Math.random() * screenHeight).toInt() //random from 0 to 1

            val offYTop = (screenHeight - binding.enemy1.height)

            enemy1Y = if (enemy1Y <= 0) 0 else if (enemy1Y >= offYTop) offYTop else enemy1Y
        }
        binding.enemy1.x =  enemy1X.toFloat()
        binding.enemy1.y =  enemy1Y.toFloat()


//        enemy2X -= screenWith / 140
        enemy2X -= screenWith / if (score in 50..99) 120 else if (score in 100 .. 199) 110
        else if (score >= 200) 100 else 140

        if (enemy2X < 0 ) {
            enemy2X = screenWith + 200
            enemy2Y = floor(Math.random() * screenHeight).toInt() //random from 0 to 1

            val offYTop = (screenHeight - binding.enemy2.height)

            enemy2Y = if (enemy2Y <= 0) 0 else if (enemy2Y >= offYTop) offYTop else enemy2Y
        }
        binding.enemy2.x =  enemy2X.toFloat()
        binding.enemy2.y =  enemy2Y.toFloat()

//        enemy3X -= screenWith / 130
        enemy3X -= screenWith / if (score in 50..99) 120 else if (score in 100 .. 199) 100
        else if (score >= 200) 80 else 130

        if (enemy3X < 0 ) {
            enemy3X = screenWith + 200
            enemy3Y = floor(Math.random() * screenHeight).toInt() //random from 0 to 1

            val offYTop = (screenHeight - binding.enemy3.height)

            enemy3Y = if (enemy3Y <= 0) 0 else if (enemy3Y >= offYTop) offYTop else enemy3Y
        }
        binding.enemy3.x =  enemy3X.toFloat()
        binding.enemy3.y =  enemy3Y.toFloat()

        coin1X -= screenWith / 120
        if (coin1X < 0 ) {
            coin1X = screenWith + 200
            coin1Y = floor(Math.random() * screenHeight).toInt() //random from 0 to 1

            val offYTop = (screenHeight - binding.coin1.height)

            coin1Y = if (coin1Y <= 0) 0 else if (coin1Y >= offYTop) offYTop else coin1Y
        }
        binding.coin1.x =  coin1X.toFloat()
        binding.coin1.y =  coin1Y.toFloat()

        coin2X -= screenWith / 100
        if (coin2X < 0 ) {
            coin2X = screenWith + 200
            coin2Y = floor(Math.random() * screenHeight).toInt() //random from 0 to 1

            val offYTop = (screenHeight - binding.enemy2.height)

            coin2Y = if (coin2Y <= 0) 0 else if (coin2Y >= offYTop) offYTop else coin2Y
        }
        binding.coin2.x =  coin2X.toFloat()
        binding.coin2.y =  coin2Y.toFloat()

    }

    private fun isCollidedWithBirds(x : Int, y : Int) : Boolean{
        return x >= birdX
                && x <= birdX + binding.bird.width
                && y >= birdY
                && y <= birdY + binding.bird.height
    }

    private fun collisionControl(){
        val centerEnemy1X = enemy1X + (binding.enemy1.width / 2)
        val centerEnemy1Y = enemy1Y + binding.enemy1.height / 2

        if(isCollidedWithBirds(centerEnemy1X, centerEnemy1Y)) {
            enemy1X = screenWith + 200
            life--
        }

        val centerEnemy2X = enemy2X + binding.enemy2.width / 2
        val centerEnemy2Y = enemy2Y + binding.enemy2.height / 2

        if (isCollidedWithBirds(centerEnemy2X, centerEnemy2Y)){
            enemy2X = screenWith + 200
            life--

        }

        val centerEnemy3X = enemy3X + binding.enemy3.width / 2
        val centerEnemy3Y = enemy3Y + binding.enemy3.height / 2

        if (isCollidedWithBirds(centerEnemy3X, centerEnemy3Y)){
            enemy3X = screenWith + 200
            life--

        }

        val centerCoin1X = coin1X + binding.coin1.width / 2
        val centerCoin1Y = coin1Y + binding.coin1.height / 2

        if (isCollidedWithBirds(centerCoin1X, centerCoin1Y)){
            coin1X = screenWith + 200
            score += 10
            binding.textViewScore.text = score.toString()
        }

        val centerCoin2X = coin2X + binding.coin2.width / 2
        val centerCoin2Y = coin2Y + binding.coin2.height / 2

        if (isCollidedWithBirds(centerCoin2X, centerCoin2Y)){
            coin2X = screenWith + 200
            score += 10
            binding.textViewScore.text = score.toString()

        }

        if (life > 0 &&  score < 300){
            if (life == 2) binding.life3.setImageResource(R.drawable.heart_grey)
            if (life == 1) binding.life2.setImageResource(R.drawable.heart_grey)
            handler.postDelayed(runnable, 20)


        } else if (score >= 300){
            handler.removeCallbacks(runnable)
            binding.constraintLayout.isEnabled = false
            binding.textViewStartInfo.visibility = VISIBLE
            binding.textViewStartInfo.text = "Congratulations! You won the game!"
            binding.enemy1.visibility = INVISIBLE
            binding.enemy2.visibility = INVISIBLE
            binding.enemy3.visibility = INVISIBLE
            binding.coin1.visibility = INVISIBLE
            binding.coin2.visibility = INVISIBLE

            handler2 = Handler()
            runnable2 = Runnable {
                birdX += (screenWith / 300)
                binding.bird.x = birdX.toFloat()
                binding.bird.y = screenHeight / 2f
                if (birdX <= screenWith) {
                    handler.postDelayed(runnable2, 20)
                }
                else{
                    handler2.removeCallbacks(runnable2)
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("score", score)
                    startActivity(intent)
                    finish() //remove activity from backstack
                }

            }
            handler2.post(runnable2)

        } else if (life == 0){
            handler.removeCallbacks(runnable)
            binding.life3.setImageResource(R.drawable.heart_grey)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
            finish()
        }
    }

}