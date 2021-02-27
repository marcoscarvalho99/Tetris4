package com.example.tetris3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tetris3.databinding.ActivityMainBinding
import com.example.tetris3.databinding.ActivityResultadoBinding

class Resultado : AppCompatActivity() {

    lateinit var binding : ActivityResultadoBinding
    lateinit var  viewmodel: ActivittyJogoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_resultado)
        viewmodel = ViewModelProvider(this).get(ActivittyJogoViewModel::class.java)

        val valor = intent.extras?.getInt("pontuacao")
        val recordmaior = intent.extras?.getInt("record")
        records(valor.toString() ,recordmaior.toString())

        binding.textViewNovoJogo.setOnClickListener {

            val intent = Intent(this, ActivityJogo::class.java)

            startActivity(intent)
            finish()
        }
        binding.textViewSaiir.setOnClickListener(){

            finish()
        }


    }

    private fun records(valo:String,recordm:String){
        if(valo==null){
            binding.pontosfinais.setText("0.0");
        }
        if(recordm==null){
            binding.textViewnovoRecorder.setText("0.0")
        }
        if(recordm!=null && valo!=null){
                    binding.pontosfinais.setText(valo.toString())
                    binding.textViewnovoRecorder.setText(recordm.toString())
        }

    }

}
