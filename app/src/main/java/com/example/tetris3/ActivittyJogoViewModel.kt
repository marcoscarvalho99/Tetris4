package com.example.tetris3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivittyJogoViewModel : ViewModel() {

    val LINHA = 36
    val COLUNA = 26

    var _pontos = MutableLiveData(0)

    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }

    fun incrementa(){
        _pontos.value = _pontos.value!!.plus(100)
    }


}