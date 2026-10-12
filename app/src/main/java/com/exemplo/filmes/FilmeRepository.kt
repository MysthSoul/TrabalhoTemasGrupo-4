package com.exemplo.filmes

/**
 * Fonte de dados única (em memória). Antes, Lista e Detalhe tinham cada um
 * a sua cópia da lista, por isso a Edição nunca conseguia alterar nada.
 */
object FilmeRepository {

    private val filmes = mutableListOf(
        Filme(1, "Inception", 2010, 9.0f),
        Filme(2, "Interstellar", 2014, 8.5f),
        Filme(3, "The Matrix", 1999, 9.5f)
    )

    fun todos(): List<Filme> = filmes.toList()

    fun porId(id: Int): Filme? = filmes.firstOrNull { it.id == id }

    fun atualizar(filme: Filme) {
        val index = filmes.indexOfFirst { it.id == filme.id }
        if (index >= 0) filmes[index] = filme
    }
}
