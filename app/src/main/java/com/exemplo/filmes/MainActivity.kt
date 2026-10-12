package com.exemplo.filmes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // O grafo já é carregado pelo NavHostFragment (app:navGraph em activity_main.xml),
        // e os deep links recebidos são tratados automaticamente. Não é preciso setGraph().
        setContentView(R.layout.activity_main)
    }
}
