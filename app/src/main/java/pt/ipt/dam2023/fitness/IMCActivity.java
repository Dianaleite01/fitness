package pt.ipt.dam2023.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IMCActivity extends AppCompatActivity {

    private EditText editTextWeight;
    private EditText editTextHeight;
    private ImageView imageViewDieta;
    private ImageView imageViewExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        imageViewDieta = findViewById(R.id.imageViewDieta);
        imageViewExercicio = findViewById(R.id.imageViewExercicio);
        Button buttonBackToMenu = findViewById(R.id.buttonBackToMenu);

        buttonCalculate.setOnClickListener(view -> calculateIMC());

        buttonBackToMenu.setOnClickListener(v -> {
            // Adicionar a lógica para ir para o Menu Principal
            Intent intent = new Intent(IMCActivity.this, MenuPrincipalActivity.class);
            startActivity(intent);
            finish(); // Opcional, dependendo do comportamento desejado
        });
    }

    private void calculateIMC() {
        try {
            double weight = Double.parseDouble(editTextWeight.getText().toString());
            double height = Double.parseDouble(editTextHeight.getText().toString());

            double imc = weight / (height * height);

            displayIMCResult(imc);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores válidos para peso e altura.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayIMCResult(double imc) {
        if (imc < 18.5) {
            displayResult("Abaixo do Peso");
            displayImages(R.drawable.dietapesobaixo, R.drawable.exerciciosobrepeso);
        } else if (imc >= 18.5 && imc < 25) {
            displayResult("Peso Normal");
            displayImages(R.drawable.dietapesonormal, R.drawable.exerciciopesonormal);
        } else if (imc >= 25 && imc < 30) {
            displayResult("Sobrepeso");
            displayImages(R.drawable.dietasobrepeso, R.drawable.exerciciosobrepeso);
        } else {
            displayResult("Obeso");
            displayImages(R.drawable.dietaobeso, R.drawable.exercicioobeso);
        }
    }

    private void displayResult(String result) {
        String message = "";

        switch (result) {
            case "Abaixo do Peso":
                message = getString(R.string.abaixo_peso_message);
                break;
            case "Peso Normal":
                message = getString(R.string.peso_normal_message);
                break;
            case "Sobrepeso":
                message = getString(R.string.sobrepeso_message);
                break;
            case "Obeso":
                message = getString(R.string.obeso_message);
                break;
        }

        Toast.makeText(this, "Resultado do IMC: " + result + "\n" + message, Toast.LENGTH_LONG).show();
    }

    private void displayImages(int dietaImageResId, int exercicioImageResId) {
        imageViewDieta.setImageResource(dietaImageResId);
        imageViewExercicio.setImageResource(exercicioImageResId);
    }


}
