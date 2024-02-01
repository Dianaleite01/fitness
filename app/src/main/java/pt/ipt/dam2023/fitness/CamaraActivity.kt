package pt.ipt.dam2023.fitness

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class CamaraActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    lateinit var button: Button
    private lateinit var adicionarbutton: Button
    private lateinit var buttonBackToMenu: Button
    private lateinit var btnVoltarMenuPrincipal: Button
    private var imageBitmap: Bitmap? = null
    private val requestimagecapture = 100
    private val mycamerapermissioncode = 101
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        requestCameraPermission()

        btnVoltarMenuPrincipal.setOnClickListener {
            val intent = Intent(this@CamaraActivity, MenuPrincipalActivity::class.java)
            startActivity(intent)
        }

        imageView = findViewById(R.id.camara)
        button = findViewById(R.id.tirarfotoButton)
        adicionarbutton = findViewById(R.id.adicionarFotoButton)
        buttonBackToMenu = findViewById(R.id.btnVoltarMenuPrincipal)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        button.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try{
                startActivityForResult(takePictureIntent,requestimagecapture)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this, "Error:" + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        buttonBackToMenu.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this@CamaraActivity, MenuPrincipalActivity::class.java)
            startActivity(intent)
            finish()
        })

        adicionarbutton.setOnClickListener{
            if (imageBitmap != null) {
                showOptionsDialog()
            } else {
                Toast.makeText(this, "Tire uma fotografia antes de adicionar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) { ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), mycamerapermissioncode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == requestimagecapture && resultCode == RESULT_OK){
            imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showOptionsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Adicionar Foto")
        builder.setMessage("Deseja adicionar esta foto ao perfil?")
        builder.setPositiveButton("Adicionar") { _, _ ->
            uploadFoto(imageBitmap!!, getUserIdFromSharedPreferences())
        }
        builder.setNegativeButton("Descartar") { _, _ ->
            // Limpar a variável imageBitmap se o user decidir descartar
            imageBitmap = null
        }
        builder.create().show()
    }

    //conversor
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }



    private fun uploadFoto(bitmap: Bitmap, userId: String) {
        val base64Image = bitmapToBase64(bitmap)
        if (userId.isEmpty()) {
            Toast.makeText(this@CamaraActivity, "ID do user não encontrado.", Toast.LENGTH_SHORT).show()
            return
        }
        saveBase64ImageToSharedPreferences(base64Image)
        val user = User(id = userId, email = "", nome = "", password = "", ftperfil = base64Image, peso = "", altura = "", codGym = 0, imc = "", dieta = "", admin = false)
        val newUserRequest = UserRequest(user)
        val token = "GonDi"
        val authHeader = "Bearer $token"
        val call = ApiService().service().createUser(newUserRequest, authHeader)
        call.enqueue(object : Callback<UserRequest> {
            override fun onResponse(call: Call<UserRequest>, response: Response<UserRequest>) {
                if (response.isSuccessful) {
                    saveUserIdToSharedPreferences(response.body()?.user?.id)
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

    private fun getUserIdFromSharedPreferences(): String {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("userId", "") ?: ""
    }

    private fun saveUserIdToSharedPreferences(userId: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.apply()
    }

    private fun saveBase64ImageToSharedPreferences(base64Image: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userImage", base64Image)
        editor.apply()
    }
}
