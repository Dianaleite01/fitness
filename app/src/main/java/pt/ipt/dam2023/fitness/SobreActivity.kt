package pt.ipt.dam2023.fitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button

class SobreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        val buttonBack: Button = findViewById(R.id.buttonBack)

        buttonBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)

            startActivity(intent)
        })
    }
}
