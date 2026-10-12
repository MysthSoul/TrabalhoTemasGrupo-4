package com.exemplo.filmes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class DetalheFragment : Fragment() {

    // [PESSOA 2] Safe Args: DetalheFragmentArgs é gerada a partir do nav_graph.xml
    private val args: DetalheFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalhe, container, false)

        val filme = FilmeRepository.porId(args.filmeId)

        // [PESSOA 3] Um deep link pode trazer um id inexistente (ex.: app://filmes/99)
        if (filme == null) {
            Toast.makeText(requireContext(), R.string.filme_nao_encontrado, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return view
        }

        view.findViewById<TextView>(R.id.txtTitulo).text = filme.titulo
        view.findViewById<TextView>(R.id.txtAno).text = filme.ano.toString()
        view.findViewById<TextView>(R.id.txtNota).text = getString(R.string.nota_valor, filme.nota)
        filme.capaUri?.let {
            runCatching { view.findViewById<ImageView>(R.id.imgCapa).setImageURI(Uri.parse(it)) }
        }

        // [PESSOA 1] Intent implícito: pede uma ação (ACTION_SEND); o sistema escolhe a app
        view.findViewById<Button>(R.id.btnPartilhar).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.partilhar_texto, filme.titulo, filme.ano))
            }
            startActivity(Intent.createChooser(intent, getString(R.string.partilhar_via)))
        }

        view.findViewById<Button>(R.id.btnEditar).setOnClickListener {
            // [PESSOA 2] Detalhe -> Edição com Safe Args
            val action = DetalheFragmentDirections.actionDetalheToEdicao(filme.id)
            findNavController().navigate(action)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // [PESSOA 2] Resultado devolvido pela Edição através do SavedStateHandle
        val handle = findNavController().currentBackStackEntry?.savedStateHandle
        handle?.getLiveData<String>("resultadoEdicao")?.observe(viewLifecycleOwner) { novoTitulo ->
            Toast.makeText(
                requireContext(), getString(R.string.titulo_atualizado, novoTitulo), Toast.LENGTH_SHORT
            ).show()
            handle.remove<String>("resultadoEdicao") // consumir, para não repetir após rodar o ecrã
        }
    }
}
