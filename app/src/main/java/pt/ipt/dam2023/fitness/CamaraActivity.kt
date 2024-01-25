package pt.ipt.dam2023.fitness

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView

class CamaraActivity : AppCompatActivity() {

    private var our_request_Code : Int =123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)
    }

    fun tirarFoto(view: View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, our_request_Code)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == our_request_Code && resultCode == RESULT_OK){
            val imageView : ImageView = findViewById(R.id.camara)
            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
        }
    }
}