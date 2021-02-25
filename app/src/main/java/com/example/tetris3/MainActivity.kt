package com.example.tetris3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tetris3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

    binding.textViewNewGame.setOnClickListener {
        val intent= Intent(this, ActivityJogo::class.java)
        startActivity(intent)
    }

    binding.textViewSetings.setOnClickListener {
        val intent= Intent(this,Config::class.java)
        startActivity(intent)
    }
        binding.textViewContinue.setOnClickListener(){
            val intent= Intent(this,Resultado::class.java)
            startActivity(intent)
        }
    }
}