package com.example.tetris3
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tetris3.databinding.ActivityConfigBinding


class Config : AppCompatActivity() {

    lateinit var binding: ActivityConfigBinding
    var dificuldade:String="entrou"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_config)

        val settings= getSharedPreferences("PREFS",Context.MODE_PRIVATE)

        dificuldade= settings.getString("dificuldade","default").toString()

        Toast.makeText(this, dificuldade, Toast.LENGTH_SHORT).show()

        //funcao para marcar o radio buton
        radio()

        binding.buttonConfirmar.setOnClickListener {



            when(binding.radioGroup.checkedRadioButtonId){

                binding.radioButtonFacil.id ->{
                    dificuldade="facil"
                    Toast.makeText(this, dificuldade, Toast.LENGTH_SHORT).show()

                }
                binding.radioButtonMedio.id ->{
                    dificuldade="medio"
                    Toast.makeText(this,dificuldade,Toast.LENGTH_SHORT).show()

                }
                binding.radioButtonDificil.id ->{
                    dificuldade="dificil"
                    Toast.makeText(this,dificuldade,Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
    override fun onStop(){
        super.onStop()

        val settings =getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        var editor = settings.edit()
        editor.putString("dificuldade",dificuldade)
        editor.commit()
    }

    private  fun radio(){
        if(dificuldade == "facil"){
            binding.radioButtonFacil.isChecked=true
            binding.radioButtonDificil.isChecked=false
            binding.radioButtonMedio.isChecked=false
        }
        else if(dificuldade == "dificil"){
            binding.radioButtonFacil.isChecked=false
            binding.radioButtonMedio.isChecked=false
            binding.radioButtonDificil.isChecked=true
        }
        else {
            binding.radioButtonFacil.isChecked=false
            binding.radioButtonMedio.isChecked=true
            binding.radioButtonDificil.isChecked=false

        }
    }
}