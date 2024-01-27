package pt.ipt.dam2023.fitness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    private val exitConfirmationCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showExitConfirmationDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            performLogin()
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegistoActivity::class.java)
            startActivity(intent)
        }

        // Adiciona o callback personalizado
        onBackPressedDispatcher.addCallback(this, exitConfirmationCallback)
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Tem a certeza que quer sair da aplicação?")

        builder.setPositiveButton("Sim") { dialog, which ->
            // Ação de logout ou fechar a aplicação
            finishAffinity()
        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Não faz nada, usuário optou por não sair
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove o callback personalizado para evitar vazamentos de memória
        exitConfirmationCallback.remove()
    }

    private fun performLogin() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Hash da senha antes de fazer a chamada à API
            val hashedPassword = hashPassword(password)
            getUsersApi(email, hashedPassword)
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUsersApi(email: String, hashedPassword: String) {
        val call = ApiService().service().getUsers()
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val users = response.body()?.users
                    if (users != null) {
                        val matchedUser = users.find { it.email == email && it.password == hashedPassword }
                        if (matchedUser != null) {
                            showSuccessMessage()
                        } else {
                            showErrorMessage("Credenciais inválidas.")
                        }
                    } else {
                        showErrorMessage("Lista de usuários nula.")
                    }
                } else {
                    showErrorMessage("Erro na chamada à API: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showErrorMessage("Erro na chamada à API: ${t.message}")
            }
        })
    }

    private fun showSuccessMessage() {
        Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MenuPrincipalActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Função de hash SHA-256 para a senha
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
