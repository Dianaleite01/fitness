package pt.ipt.dam2023.fitness

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.util.Base64
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class CamaraActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var button: Button
    lateinit var adicionarbutton: Button
    var imageBitmap: Bitmap? = null
    val REQUEST_IMAGE_CAPTURE = 100
    val MY_CAMERA_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        requestCameraPermission()

        imageView = findViewById(R.id.camara)
        button = findViewById(R.id.tirarfotoButton)
        adicionarbutton = findViewById(R.id.adicionarFotoButton)

        button.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try{
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this, "Error:" + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        adicionarbutton.setOnClickListener{
            if (imageBitmap != null) {
                uploadFoto(imageBitmap!!, "UserId")
            } else {
                Toast.makeText(this, "Tire uma fotografia antes de adicionar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                MY_CAMERA_PERMISSION_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun tirarFoto(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    fun uploadFoto(bitmap: Bitmap, userId: String) {
        // Converter para base64
        val base64Image = bitmapToBase64(bitmap)
        val user = User(id = userId, email = "", nome = "", password = "", ftperfil = base64Image, peso = "", altura = "", codGym = 0, imc = "", dieta = "", admin = false)
        // Cria um novo userRequest
        val userRequest = UserRequest(user)
        // envia para a API o userResquest
        val call = ApiService().service().createUser(userRequest)
        call.enqueue(object : Callback<UserRequest> {
            override fun onResponse(call: Call<UserRequest>, response: Response<UserRequest>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CamaraActivity, "Foto de perfil atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CamaraActivity, "Erro ao atualizar a foto do perfil. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserRequest>, t: Throwable) {
                Toast.makeText(this@CamaraActivity, "Erro de conexão. Verifique sua conexão com a internet e tente novamente.", Toast.LENGTH_SHORT).show()
            }
        })
    }

}