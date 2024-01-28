package pt.ipt.dam2023.fitness

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MenuPrincipalActivity : AppCompatActivity() {

    //DECLARACAO DAS VARIAVEIS
    private lateinit var qrcodeCard: CardView
    private lateinit var galeriaCard: CardView
    private lateinit var imcCard: CardView
    private lateinit var corridaCard: CardView
    private lateinit var calendarioCard: CardView
    private lateinit var logoutCard: CardView
    private lateinit var txtNome: TextView
    private lateinit var fotoPerfil: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuprincipal)

        txtNome = findViewById(R.id.txtNome)
        fotoPerfil = findViewById(R.id.fotoPefil)

        //INICIALIZA AS REFERENCIAS PARA OS CARDVIEW USANDO FINDVIEWBYID
        qrcodeCard = findViewById(R.id.qrcodeCard)
        galeriaCard = findViewById(R.id.galeriaCard)
        imcCard = findViewById(R.id.imcCard)
        corridaCard = findViewById(R.id.corridaCard)
        calendarioCard = findViewById(R.id.calendarioCard)
        logoutCard = findViewById(R.id.logoutCard)


        // Obtém a string base64 da SharedPreferences
        val base64Image = getBase64ImageFromSharedPreferences()

        if (base64Image != null) {
            // Converte a string de base64 para Bitmap
            val bitmapImage = base64ToBitmap(base64Image)
            // Define o Bitmap na ImageView
            if (bitmapImage != null) {
                fotoPerfil.setImageBitmap(bitmapImage)
            } else {
                Log.e("MenuPrincipalActivity", "Falha ao converter base64 para bitmap.")
            }
        } else {
            Log.e("MenuPrincipalActivity", "Base64 da imagem não encontrado.")
            fotoPerfil.setImageResource(R.drawable.ic_launcher_foreground)
        }

        //CONFIGURACAO DO ONCLICKLISTENER PARA CADA CARDVIEW

        qrcodeCard.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity, QrCodeActivity::class.java)
            startActivity(intent)
        }

        galeriaCard.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity, CamaraActivity::class.java)
            startActivity(intent)
        }

        imcCard.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity, IMCActivity::class.java)
            startActivity(intent)
        }

        corridaCard.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity, CorridaActivity::class.java)
            startActivity(intent)
        }

        calendarioCard.setOnClickListener {
            val intent = Intent(this@MenuPrincipalActivity, CalendarioActivity::class.java)
            startActivity(intent)
        }

        logoutCard.setOnClickListener {
            showLogoutConfirmationDialog()
        }

    }
    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
    }

    private fun getBase64ImageFromSharedPreferences(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("userImage", null)
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Tem a certeza que quer sair da aplicação?")

        builder.setPositiveButton("Sim") { dialog, which ->
            // Ação de logout
            val intent = Intent(this@MenuPrincipalActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Não faz nada, user optou por não sair
        }

        val dialog = builder.create()
        dialog.show()
    }
}