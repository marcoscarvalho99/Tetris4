package com.example.tetris3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tetris3.Pecas.*
import com.example.tetris3.databinding.ActivityJogoBinding
import kotlin.random.Random


class ActivityJogo : AppCompatActivity() {

    val LINHA = 36
    val COLUNA = 26
    var pause = true;
    var cordX = 1;
    var cordY = 13;
    var running = true
    var speed: Long = 300
    var aleatorio: Int = 0
    lateinit var peca: Peca
    var dificuldade:String="medio"
    lateinit var binding: ActivityJogoBinding
    lateinit var  viewmodel: ActivittyJogoViewModel


    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }

    var boardView = Array(LINHA) {
        arrayOfNulls<ImageView>(COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jogo)
        viewmodel = ViewModelProvider(this).get(ActivittyJogoViewModel::class.java)
        binding.gridboard.rowCount = LINHA
        binding.gridboard.columnCount = COLUNA
        //inflador
        val inflater = LayoutInflater.from(applicationContext)

        val settings= getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        dificuldade= settings.getString("dificuldade","default").toString()
        mudarDificuldade(dificuldade)
        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, binding.gridboard, false) as ImageView
                binding.gridboard.addView(boardView[i][j])
            }
        }
        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {

                boardView[i][j]!!.setImageResource(R.drawable.gray);
            }
        }

        gameRun()
    }


    fun gameRun() {


        Thread {

            aleatorio = Random.nextInt(4 - 0)

            escolherPeca(aleatorio)


            while (running) {
                binding.imageButtonPause.setOnClickListener() {
                    pause = !pause
                }


                if (pause) {

                    Thread.sleep(speed)
                    runOnUiThread {
                        //limpa tela
                        for (i in 1 until LINHA - 1) {
                            for (j in 1 until COLUNA - 1) {
                                if (board[i][j] == 1) {
                                    boardView[i][j]!!.setImageResource(R.drawable.white)
                                } else {
                                    boardView[i][j]!!.setImageResource(R.drawable.black)
                                }

                            }
                        }


                        //move peça atual para esquerda

                        binding.imageButtonEsquerda.setOnClickListener() {

                            if (paraEsquerda(peca.getPontos())) {
                                peca.moveLeft()
                            }
                        }
                        //direita
                        binding.imageButtonDireita.setOnClickListener() {
                            if (paraDireita(peca.getPontos())) {
                                peca.moveRight()
                            }
                        }

                        binding.imageButtonBaixo.setOnClickListener() {
                            speed = 100
                        }

                        binding.imageButtonGirarPeca.setOnClickListener {
                            Toast.makeText(this, "botaogirar", Toast.LENGTH_SHORT).show()

                            girarPeca()
                            Thread.sleep(300)
                        }

                        if (baixar(peca.getPontos())) {
                            peca.moveDown()

                        } else {
                            guardarposiccao(peca.getPontos())
                            aleatorio = Random.nextInt(4 - 0)
                            escolherPeca(aleatorio)
                            speed = 300;
                        }
                        if (board[1][14] == 1) {
                            running = false
                        }

                        //print peça
                        try {

                            printarPeca()

                        } catch (e: ArrayIndexOutOfBoundsException) {
                            //se a peça passou das bordas eu vou parar o jogo
                            running = false
                        }

                    }
                }

            }
        }.start()
    }

    fun escolherPeca(tipo: Int) {
        when (tipo) {
            0 -> {
                peca = Quadrado(cordX, cordY)
            }
            1 -> {
                peca = Triangulo(cordX, cordY)
            }
            2 -> {
                peca = Coluna(cordX, cordY)
            }
            3 -> {
                peca = LetraLEsquerda(cordX, cordY)
            }
            else -> {
                peca = LetraSDireita(cordX, cordX)
            }

        }

    }


    fun printarPeca() {

        try {
            peca.getPontos().forEach {

                boardView[it.x][it.y]!!.setImageResource(R.drawable.white)

            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            //se a peça passou das bordas eu vou parar o jogo
            running = false

        }
    }


    private fun borda(p: Ponto): Boolean {
        return p.y == 0 || p.x == LINHA - 1 || p.y == COLUNA - 1 || p.x == 0
    }

    private fun posicaoValida(p: Ponto): Boolean {
        if (
                (board[p.x][p.y - 1] == 1 && board[p.x + 1][p.y] == 1) ||
                (board[p.x][p.y + 1] == 1 && board[p.x + 1][p.y] == 1) || (board[p.x + 1][p.y] == 1)
        ) {
            return true
        }
        return false
    }

    private fun baixar(p: Array<Ponto>): Boolean {
        p.forEach {
            if (borda(Ponto(it.x + 1, it.y)) || posicaoValida(it)) {
                return false
            }
        }
        return true
    }

    private fun guardarposiccao(p: Array<Ponto>) {
        p.forEach {
            board[it.x][it.y] = 1
        }
    }

    private fun paraDireita(p: Array<Ponto>): Boolean {

        p.forEach {
            if (board[it.x][it.y + 1] == 1 || borda(Ponto(it.x, it.y + 1))) {
                return false
            }
        }
        return true
    }

    private fun paraEsquerda(p: Array<Ponto>): Boolean {
        p.forEach {
            if (board[it.x][it.y - 1] == 1 || borda(Ponto(it.x, it.y - 1))) {
                return false
            }
        }
        return true
    }


    private fun girarPeca() {
        when (peca) {
            is Quadrado -> {
                //peca.rotacionar()
            }
            is Coluna -> {
                var pontos = peca.rotacionar()
                if (paraEsquerda(pontos) || paraDireita(pontos) || baixar(pontos)) {
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as Coluna).orientacao)
                } else {

                }
            }
            is Triangulo -> {
                var pontos = peca.rotacionar()
                if (paraEsquerda(pontos) || paraDireita(pontos) || baixar(pontos)) {
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as Triangulo).orientacao)
                } else {

                }
            }
            is LetraSDireita -> {
                var pontos = peca.rotacionar()
                if (paraEsquerda(pontos) || paraDireita(pontos) || baixar(pontos)) {
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as LetraSDireita).orientacao)
                }
            }
            is LetraLEsquerda -> {
                var pontos = peca.rotacionar()
                if (paraEsquerda(pontos) || paraEsquerda(pontos) || baixar(pontos)) {
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as LetraLEsquerda).orientacao)
                }
            }


        }
    }

    private fun mudarDificuldade(dificuldad:String){
        Toast.makeText(this,dificuldad,Toast.LENGTH_SHORT).show()
        when(dificuldad){
            "facil" ->{
                speed=400

                binding.textViewDificuldade.setText(dificuldad)
            }
            "medio" ->{
                speed=300
                binding.textViewDificuldade.setText(dificuldad)
            }
            "dificil" ->{
                speed=200
                binding.textViewDificuldade.setText(dificuldad)
            }
            else ->{
                Toast.makeText(this,"erro na dificuldade",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
