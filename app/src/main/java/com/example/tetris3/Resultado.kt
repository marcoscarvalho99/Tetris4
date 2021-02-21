package com.example.tetris3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tetris3.databinding.ActivityMainBinding
import com.example.tetris3.databinding.ActivityResultadoBinding

class Resultado : AppCompatActivity() {
lateinit var binding : ActivityResultadoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

binding = DataBindingUtil.setContentView(this,R.layout.activity_resultado)

    }
}