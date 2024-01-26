package pt.ipt.dam2023.fitness

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.*

class QrCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        val qrImage = findViewById<ImageView>(R.id.idIVQrcode)
        val backButton = findViewById<Button>(R.id.idBtnBack)

        val qrContent = "Informação do QR " + UUID.randomUUID().toString()
        try {
            val qrBitmap = generateQRCode(qrContent)
            qrImage.setImageBitmap(qrBitmap)
        } catch (e: Exception) {
            // Handle error
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateQRCode(content: String): Bitmap {
        val size = 500 // Definir um tamanho fixo para o QR Code
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size)
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.createBitmap(bitMatrix)
    }
}
