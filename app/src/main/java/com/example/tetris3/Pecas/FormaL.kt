package com.example.tetris3.Pecas


class FormaL(var linha:Int,var coluna:Int): Peca(Ponto(linha,coluna),
    Ponto(linha+1,coluna),
    Ponto(linha+1,coluna-1),
    Ponto(linha+1,coluna-2)) {

}