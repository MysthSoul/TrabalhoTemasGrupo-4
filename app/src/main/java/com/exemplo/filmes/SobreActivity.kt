package com.exemplo.filmes

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**  Destino do Intent explícito lançado a partir da lista. */
class SobreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        val grupo = intent.getStringExtra("grupo") ?: "—"
        findViewById<TextView>(R.id.textGrupo).text = getString(R.string.sobre_grupo, grupo)
    }
}
