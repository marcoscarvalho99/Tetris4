package com.example.tetris3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tetris3.databinding.ActivityMainBinding
import com.example.tetris3.databinding.ActivityResultadoBinding

class Resultado : AppCompatActivity() {
lateinit var binding : ActivityResultadoBinding
    var record=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_resultado)
       val valor = intent.extras?.getString("pontuacao")
        val settings= getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        record= settings.getInt("record",0)
        var editor = settings.edit()
        binding.pontosfinais.setText(valor+" atual");




    //    var aux:String=record.toString()
      //  binding.textViewnovoRecorder.setText(aux)
        //binding.pontosfinais.setText(record.toString()+"pontos")
        binding.textViewNovoJogo.setOnClickListener {

            val intent = Intent(this, ActivityJogo::class.java)
            finish()
            startActivity(intent)
        }


        binding.textViewSaiir.setOnClickListener(){
            val  intent =Intent(this,MainActivity::class.java)
            finish()
            startActivity(intent);
        }


    }
    private fun verificarRecorder( valor :Int){
    if(record<=valor){

    }

    }
    override fun onStop(){
        super.onStop()

        val settings =getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        var editor = settings.edit()
        editor.putInt("record",record)
        editor.commit()
    }
}