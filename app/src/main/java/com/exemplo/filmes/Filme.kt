package com.exemplo.filmes

data class Filme(
    val id: Int,
    val titulo: String,
    val ano: Int,
    val nota: Float = 0f,
    val capaUri: String? = null
)
