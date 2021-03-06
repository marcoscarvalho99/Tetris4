package com.example.tetris3

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tetris3.Pecas.*
import com.example.tetris3.databinding.ActivityJogoBinding
import kotlin.random.Random


class ActivityJogo : AppCompatActivity() {
    val REQUEST_CODE = 1
    val LINHA = 36
    val COLUNA = 26
    var pause = false;
    var cordX = 1;
    var cordY = 8;
    var running = true
    var speed: Long = 300
    var aleatorio: Int = 0
    var cont:Int=1
    var record:Int=0
    var recordAtual=0;
    var dificuldade:String="medio"

    lateinit var binding: ActivityJogoBinding
    lateinit var  viewmodel: ActivittyJogoViewModel

    private lateinit var proximaPeca: Peca
    private lateinit var peca: Peca


    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }

    var boardView = Array(LINHA) {
        arrayOfNulls<ImageView>(COLUNA)
    }
    private var pecaView = Array(4){
        arrayOfNulls<ImageView>(12)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jogo)
        viewmodel = ViewModelProvider(this).get(ActivittyJogoViewModel::class.java)
        binding.gridboard.rowCount = LINHA
        binding.gridboard.columnCount = COLUNA
        binding.viewModel = viewmodel
        binding.lifecycleOwner=this


        binding.imageButtonPause.setOnClickListener() {

            var intent = Intent(this, MainActivity::class.java)
            var param = Bundle()
            param.putInt("continuar", 1)
            intent.putExtras(param)
            startActivityForResult(intent, REQUEST_CODE)
        }


        //inflador
        val inflater = LayoutInflater.from(applicationContext)
        val settings= getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        dificuldade= settings.getString("dificuldade","default").toString()

        record=settings.getInt("record",1)

        binding.newPeca.rowCount = 2
        binding.newPeca.columnCount = 6

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, binding.gridboard, false) as ImageView
                binding.gridboard.addView(boardView[i][j])
            }
        }

        for (i in 0 until 4) {
            for (j in 0 until 6) {
                pecaView[i][j] = inflater.inflate(R.layout.inflate_image_view, binding.newPeca, false) as ImageView
                binding.newPeca.addView(pecaView[i][j])
            }
        }

        mudarDificuldade(dificuldade)

        gameRun()
    }


    //INICIA O JOGO AO CHAMAR
    fun gameRun() {
        Thread {

            aleatorio = Random.nextInt(5 - 0)
            peca = escolherPeca(aleatorio)

            aleatorio = Random.nextInt(5 - 0)
            proximaPeca = escolherPeca(aleatorio)

            while (running) {
                if(pause== true){
                    while (pause){ }
                }else{

                    Thread.sleep(speed)
                    runOnUiThread {
                        //limpa tela
                        for (i in 0 until LINHA ) {
                            for (j in 0 until COLUNA ) {
                                if(borda(Ponto(i,j))){
                                    boardView[i][j]!!.setImageResource(R.drawable.gray)
                                }

                                else if (viewmodel.board[i][j] == 1) {
                                    boardView[i][j]!!.setImageResource(R.drawable.white)
                                } else {
                                    boardView[i][j]!!.setImageResource(R.drawable.black)
                                }

                            }
                        }

                        binding.imageButtonEsquerda.setOnClickListener() {
                            if (paraEsquerda(peca.getPontos())) {
                                peca.moveLeft()
                            }
                        }
                        binding.imageButtonDireita.setOnClickListener() {
                            if (paraDireita(peca.getPontos())) {
                                peca.moveRight()
                            }
                        }
                        binding.imageButtonBaixo.setOnClickListener() {
                            speed = 100
                        }
                        binding.imageButtonGirarPeca.setOnClickListener {
                            girarPeca()
                            Thread.sleep(300)
                        }
                        if(topo(peca.getPontos())){
                            irParaResultado() //mudar para tela de resultado e passa parametros
                        }
                        else  if (baixar(peca.getPontos())) {
                            ///move a peca para baixo
                            peca.moveDown()
                            //atualiza o tabuleiro e marca os pontos
                            pontuar()
                        } else {
                            //salvar os pontos e mostra
                            pontuar()
                            //atualiza o tabuleiro e marca os pontos
                            guardarposiccao(peca.getPontos())

                            aleatorio = Random.nextInt(5 - 0)
                            peca = proximaPeca
                            proximaPeca = escolherPeca(aleatorio)

                            //volta s precisar para a velocidade certa
                            mudarDificuldade(dificuldade)
                        }
                        //print peça atual e a proxima
                        try {
                            printarPeca()
                            exibirProximaPeca()

                        } catch (e: ArrayIndexOutOfBoundsException) {
                            //se a peça passou das bordas eu vou parar o jogo
                            Toast.makeText(this,"passou do limite da matriz",Toast.LENGTH_SHORT).show()
                        }
                    }
                };
            }
        }.start()
    }

     fun irParaResultado(){
         if(cont==3){
             running = false
             val i = Intent(this, Resultado::class.java)

             var vale=viewmodel._pontos.value
                recordAtual=vale!!;
             verificarRecord(vale!!) //verifica o recorde atual e o big record

             i.putExtra("record",record)
             i.putExtra("pontuacao",vale)
             finish()
             startActivity(i)
         }
         cont++
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
    override fun onStop(){
        super.onStop()
        val settings =getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        var editor = settings.edit()
        editor.putInt("record",record)
        editor.putInt("ultimorecord",recordAtual);
        editor.commit()
    }
    override fun onResume() {
        super.onResume()
        Log.i("AULA", "onResume() invocado.")
        pause=false
    }
    override fun onStart() {
        Log.i("AULA", "onStart() invocado.")
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        Log.i("AULA", "onPause invocado.")
        pause = true
    }


     private fun verificarRecord(recordAgora:Int){
         if(recordAgora> record){
             record=recordAgora;

         }
     }

    private fun topo(p: Array<Ponto>):Boolean {
        p.forEach {
            if (it.x == 1 && viewmodel.board[it.x+1][it.y] == 1) {

                return true
            }
        }
        return false
    }

    fun escolherPeca(tipo: Int):Peca {

          return  when (tipo) {
                0 -> {
                     Quadrado(cordX, cordY)
                }
                1 -> {
                     Triangulo(cordX, cordY)
                }
                2 -> {
                     Coluna(cordX, cordY)
                }
                3 -> {
                    FormaL(cordX, cordY)
                }
                4 -> {
                  LetraSEsquerda(cordX, cordY)
                }
                else -> {
                    LetraSDireita(cordX, cordX)
                }

            }
    }
    private fun exibirProximaPeca(){
        for (i in 0 until 4) {
            for (j in 0 until 6) {
                pecaView[i][j]!!.setImageResource(R.drawable.semfundo)
            }
        }
        try {
            proximaPeca.getPontos().forEach {
                pecaView[it.x][it.y-6]!!.setImageResource(R.drawable.white)
            }
        }catch (e:ArrayIndexOutOfBoundsException){
            Toast.makeText(this, "Saiu da area da matriz", Toast.LENGTH_SHORT).show()
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
                (viewmodel.board[p.x][p.y - 1] == 1 && viewmodel.board[p.x + 1][p.y] == 1) ||
                (viewmodel.board[p.x][p.y + 1] == 1 && viewmodel.board[p.x + 1][p.y] == 1) || (viewmodel.board[p.x + 1][p.y] == 1)
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
            viewmodel.board[it.x][it.y] = 1
        }
    }

    private fun paraDireita(p: Array<Ponto>): Boolean {
        p.forEach {
            if (viewmodel.board[it.x][it.y + 1] == 1 || borda(Ponto(it.x, it.y + 1))) {
                return false
            }
        }
        return true
    }

    private fun paraEsquerda(p: Array<Ponto>): Boolean {
        p.forEach {
            if (viewmodel.board[it.x][it.y - 1] == 1 || borda(Ponto(it.x, it.y - 1))) {
                return false
            }
        }
        return true
    }

    private fun girarPeca() {
        when (peca) {
            is Quadrado -> {
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
            is LetraSEsquerda -> {
                var pontos = peca.rotacionar()
                if (paraEsquerda(pontos) || paraEsquerda(pontos) || baixar(pontos)) {
                    peca.setPontos(pontos)
                    peca.getPontos().forEach {
                        it.moveUp()
                    }
                    peca.setOrietacaPeca((peca as LetraSEsquerda).orientacao)
                }
            }

        }
    }

    private fun mudarDificuldade(dificuldad:String){

        when(dificuldad){
            "facil" ->{
                speed=350

                binding.textViewDificuldade.setText(dificuldad)
            }
            "medio" ->{
                speed=250
                binding.textViewDificuldade.setText(dificuldad)
            }
            "dificil" ->{
                speed=150
                binding.textViewDificuldade.setText(dificuldad)
            }
            else ->{
                Toast.makeText(this,"erro na dificuldade",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pontuar(){
        if(ordenarTabuleiro(checarLinhaCompleta())) {

            viewmodel.incrementa()
        }
    }

    private fun ordenarTabuleiro(linhaCompleta:Int):Boolean{
        if(linhaCompleta == 0){
            return false
        }
        var i = linhaCompleta
        while (i>0){
            for (j in 1 until COLUNA-1){
                viewmodel.board[i][j] = viewmodel.board[i-1][j]
                viewmodel.board[i-1][j] = 0
            }
            i--
        }
        return true
    }

    private fun checarLinhaCompleta():Int{
        var countPeca = 0
        for (i in 1 until LINHA-1) {
            for (j in 1 until COLUNA-1) {
                if(viewmodel.board[i][j] == 1){
                    countPeca++
                }
            }
            if(countPeca == COLUNA-2){
                return i
            }else{
                countPeca = 0
            }
        }
        return 0
    }

}
