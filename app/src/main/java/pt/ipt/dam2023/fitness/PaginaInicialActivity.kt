package pt.ipt.dam2023.fitness

import pt.ipt.dam2023.fitness.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class PaginaInicialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paginainicial)

        val btnCriarConta = findViewById<Button>(R.id.btnCriarConta)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnCriarConta.setOnClickListener { // Lógica para a tela de criar conta
            val intent = Intent(this@PaginaInicialActivity, RegistoActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener { // Lógica para a tela de login
            val intent = Intent(this@PaginaInicialActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}