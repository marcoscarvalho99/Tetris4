package com.example.tetris3.Pecas

class Coluna(linha:Int, coluna:Int, var orientacao: Int = 1): Peca(
        Ponto(linha,coluna-2),
        Ponto(linha,coluna-1),
        Ponto(linha,coluna),
        Ponto(linha,coluna+1)
) {
    override fun rotacionar():Array<Ponto>{
        val p = getPontos()
        when(orientacao){
            1 -> {
                //Está na Horizontal
                return arrayOf(
                        Ponto(p[0].x+2, p[0].y+2),
                        Ponto(p[1].x+1, p[1].y+1),
                        Ponto(p[2].x, p[2].y),
                        Ponto(p[3].x-1, p[3].y-1))
            }
            2 -> {
                //Está na Vertical
                val tempPeca =  arrayOf(
                        Ponto(p[0].x-2, p[0].y-2),
                        Ponto(p[1].x-1, p[1].y-1),
                        Ponto(p[2].x,p[2].y),
                        Ponto(p[3].x+1, p[3].y+1))
                tempPeca.forEach {
                    it.moveRight()
                    it.moveRight()
                }
                return tempPeca
            }
        }
        return arrayOf()
    }

    override fun setOrietacaPeca(o:Int){
        when(o){
            1 -> {
                orientacao = 2
            }
            2 -> {
                orientacao = 1
            }
        }

    }
}