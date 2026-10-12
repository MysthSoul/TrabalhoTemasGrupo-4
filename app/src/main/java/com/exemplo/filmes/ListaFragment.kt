package com.exemplo.filmes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerFilmes)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = FilmeAdapter(FilmeRepository.todos()) { filme ->
            // [PESSOA 2] Navigation Component + Safe Args: Lista -> Detalhe com o id
            val action = ListaFragmentDirections.actionListaToDetalhe(filme.id)
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.btnSobre).setOnClickListener {
            // [PESSOA 1] Intent explícito: destino conhecido + extra
            val intent = Intent(requireContext(), SobreActivity::class.java).apply {
                putExtra("grupo", "Grupo 4")
            }
            startActivity(intent)
        }

        return view
    }
}
