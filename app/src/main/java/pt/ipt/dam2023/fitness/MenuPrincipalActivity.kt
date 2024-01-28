package pt.ipt.dam2023.fitness

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuprincipal)

        //INICIALIZA AS REFERENCIAS PARA OS CARDVIEW USANDO FINDVIEWBYID
        qrcodeCard = findViewById(R.id.qrcodeCard)
        galeriaCard = findViewById(R.id.galeriaCard)
        imcCard = findViewById(R.id.imcCard)
        corridaCard = findViewById(R.id.corridaCard)
        calendarioCard = findViewById(R.id.calendarioCard)
        logoutCard = findViewById(R.id.logoutCard)


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
            val intent = Intent(this@MenuPrincipalActivity, SobreActivity::class.java)
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
            // Não faz nada, usuário optou por não sair
        }

        val dialog = builder.create()
        dialog.show()
    }
}