package com.exemplo.filmes

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class EdicaoFragment : Fragment() {

    private val args: EdicaoFragmentArgs by navArgs()
    private var capaUri: Uri? = null

    // [PESSOA 1] Activity Result API: registar uma vez (contrato + callback)...
    private val pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            capaUri = uri
            view?.findViewById<ImageView>(R.id.imgCapa)?.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edicao, container, false)

        val filme = FilmeRepository.porId(args.filmeId)
        if (filme == null) {
            Toast.makeText(requireContext(), R.string.filme_nao_encontrado, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return view
        }

        val editTitulo = view.findViewById<EditText>(R.id.editTitulo)
        editTitulo.setText(filme.titulo)

        if (capaUri == null) capaUri = filme.capaUri?.let { Uri.parse(it) }
        capaUri?.let {
            runCatching { view.findViewById<ImageView>(R.id.imgCapa).setImageURI(it) }
        }

        view.findViewById<Button>(R.id.btnEscolherImagem).setOnClickListener {
            // ...e lançar quando o utilizador carrega no botão
            pickMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        view.findViewById<Button>(R.id.btnGravar).setOnClickListener {
            val novoTitulo = editTitulo.text.toString().trim()
            if (novoTitulo.isEmpty()) {
                editTitulo.error = getString(R.string.titulo_obrigatorio)
                return@setOnClickListener
            }
            FilmeRepository.atualizar(filme.copy(titulo = novoTitulo, capaUri = capaUri?.toString()))

            // [PESSOA 2] Devolver um resultado ao destino anterior (Detalhe)
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "resultadoEdicao", novoTitulo
            )
            findNavController().popBackStack()
        }

        return view
    }
}
