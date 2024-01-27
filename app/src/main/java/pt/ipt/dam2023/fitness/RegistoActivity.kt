package pt.ipt.dam2023.fitness

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.util.UUID

class RegistoActivity : AppCompatActivity() {
    private lateinit var editTextNome: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextSenha: EditText
    private lateinit var editTextConfirmarSenha: EditText
    private lateinit var editTextCodigoGinasio: EditText
    private lateinit var buttonRegistar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo)

        editTextNome = findViewById(R.id.RegistoNome)
        editTextEmail = findViewById(R.id.RegistoEmail)
        editTextSenha = findViewById(R.id.RegistoSenha)
        editTextConfirmarSenha = findViewById(R.id.RegistoConfirmarSenha)
        editTextCodigoGinasio = findViewById(R.id.RegistoCodigoGinasio)
        buttonRegistar = findViewById(R.id.btnRegistar)

        buttonRegistar.setOnClickListener {
            performRegistration()
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun performRegistration() {
        val nome = editTextNome.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val senha = editTextSenha.text.toString().trim()
        val confirmarSenha = editTextConfirmarSenha.text.toString().trim()
        val codigoGinasio = editTextCodigoGinasio.text.toString().trim()

        if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && confirmarSenha.isNotEmpty() && codigoGinasio.isNotEmpty()) {
            if (senha == confirmarSenha) {
                val token = "GonDi"
                val authHeader = "Bearer $token"
                val call = ApiService().service().getUsers(authHeader)
                call.enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        if (response.isSuccessful) {
                            val users = response.body()?.users
                            if (users != null) {
                                val emailExists = users.any { it.email == email }
                                if (emailExists) {
                                    Toast.makeText(
                                        this@RegistoActivity,
                                        "O e-mail já está em uso. Por favor, tente novamente.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (users.any { it.codGym == codigoGinasio.toInt() }) {
                                    registerUser(nome, email, senha, codigoGinasio.toInt())
                                } else {
                                    Toast.makeText(
                                        this@RegistoActivity,
                                        "Código do ginásio inválido.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@RegistoActivity,
                                "Erro ao verificar o código do ginásio. Tente novamente mais tarde.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Toast.makeText(
                            this@RegistoActivity,
                            "Erro de conexão. Verifique sua conexão com a internet e tente novamente.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                Toast.makeText(
                    this,
                    "As senhas não correspondem. Por favor, tente novamente.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                "Por favor, preencha todos os campos antes de registar.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun registerUser(nome: String, email: String, senha: String, codigoGinasio: Int) {
        val uniqueID = UUID.randomUUID().toString()
        val hashedPassword = hashPassword(senha) // Hash da senha
        val newUser = User(id = uniqueID, email = email, nome = nome, password = hashedPassword, ftperfil = "", peso = "", altura = "", codGym = codigoGinasio, imc = "", dieta = "", admin = false)
        val newUserRequest = UserRequest(newUser)
        val token = "GonDi"
        val authHeader = "Bearer $token"
        val call = ApiService().service().createUser(newUserRequest, authHeader)
        call.enqueue(object : Callback<UserRequest> {
            override fun onResponse(call: Call<UserRequest>, response: Response<UserRequest>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegistoActivity, "Registo bem-sucedido!", Toast.LENGTH_SHORT).show()
                    // Navegar para a atividade de login
                    val intent = Intent(this@RegistoActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegistoActivity, "Erro ao realizar registo. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserRequest>, t: Throwable) {
                Toast.makeText(this@RegistoActivity, "Erro de conexão. Verifique sua conexão com a internet e tente novamente.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
