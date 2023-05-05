package com.example.newflappybird

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.newflappybird.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var animation: Animation
    private lateinit var mediaPlayer: MediaPlayer
    private var status = false  // meaning audio is playing
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        binding.bird.animation = animation
        binding.enemy1.animation = animation
        binding.enemy2.animation = animation
        binding.enemy3.animation = animation
        binding.coin.animation = animation

    }

    override fun onResume() {
        super.onResume()
        mediaPlayer = MediaPlayer.create(this, R.raw.another_seasons)
        mediaPlayer.start()

        binding.volume.setOnClickListener {
            status = if (status){
                mediaPlayer.setVolume(1f,1f)
                binding.volume.setImageResource(R.drawable.baseline_volume_up_24)
                false
            } else{
                mediaPlayer.setVolume(0f,0f)
                binding.volume.setImageResource(R.drawable.baseline_volume_off_24)
                true
            }
        }
        binding.buttonStart.setOnClickListener {
            mediaPlayer.reset()
            binding.volume.setImageResource(R.drawable.baseline_volume_up_24)

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)

        }

    }
}