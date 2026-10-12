package com.exemplo.filmes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmeAdapter(
    private val filmes: List<Filme>,
    private val onClick: (Filme) -> Unit
) : RecyclerView.Adapter<FilmeAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(android.R.id.text1)
        val ano: TextView = view.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val filme = filmes[position]
        holder.titulo.text = filme.titulo
        holder.ano.text = filme.ano.toString()
        holder.itemView.setOnClickListener { onClick(filme) }
    }

    override fun getItemCount() = filmes.size
}