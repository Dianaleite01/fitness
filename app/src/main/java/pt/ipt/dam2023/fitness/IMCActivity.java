package pt.ipt.dam2023.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
            Intent intent = new Intent(IMCActivity.this, MenuPrincipalActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void calculateIMC() {
        try {
            double weight = Double.parseDouble(editTextWeight.getText().toString());
            double height = Double.parseDouble(editTextHeight.getText().toString());

            double imc = weight / (height * height);

            displayIMCResult(imc);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores válidos para o peso e a altura.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayIMCResult(double imc) {
        if (imc < 18.5) {
            displayResult("Abaixo do Peso");
            displayImages(R.drawable.dietapesobaixo, R.drawable.exerciciosobrepeso, "Estás abaixo do peso ideal. Recomenda-se incluir alimentos ricos em proteínas e calorias para aumentar a ingestão calórica. Consulte um nutricionista para desenvolver um plano personalizado, considerando as suas necessidades alimentares específicas. Adicionalmente, um treino leve com ênfase em ganho de massa muscular pode ser benéfico para fortalecer o corpo.", "Sugere-se um treino leve, com foco em ganho de massa muscular. Isso pode incluir exercícios de resistência e treinamento com pesos para fortalecer os músculos e melhorar a composição corporal.");
        } else if (imc >= 18.5 && imc < 25) {
            displayResult("Peso Normal");
            displayImages(R.drawable.dietapesonormal, R.drawable.exerciciopesonormal, "Parabéns! Estás está dentro da faixa de peso que é considerado normal. Manter uma dieta equilibrada é fundamental para sustentar a sua saúde. Certifique-se de incluir alimentos de todos os grupos alimentares, como frutas, vegetais, proteínas magras e grãos integrais.", "Para manter a sua saúde geral, é recomendado incorporar um treino regular. Isso pode incluir atividades como caminhadas, corridas, natação ou qualquer forma de exercício que aprecie.");
        } else if (imc >= 25 && imc < 30) {
            displayResult("Sobrepeso");
            displayImages(R.drawable.dietasobrepeso, R.drawable.exerciciosobrepeso, "Estás com sobrepeso, o que pode aumentar o risco de problemas de saúde. Recomenda-se reduzir a ingestão de calorias e focar em alimentos nutritivos. Incluir mais frutas, vegetais e grãos integrais na sua dieta pode ser benéfico para a perda de peso.", "Incorporar treinos cardiovasculares, como corrida, ciclismo ou aeróbica, pode ser eficaz para queimar calorias. Além disso, incluir treinos de resistência pode ajudar a fortalecer os músculos e melhorar a composição corporal.");
        } else {
            displayResult("Obeso");
            displayImages(R.drawable.dietaobeso, R.drawable.exercicioobeso, "Está classificado como obeso, o que pode aumentar significativamente o risco de várias condições de saúde. Recomenda-se seguir um plano de dieta com restrição calórica, focado em alimentos saudáveis e nutritivos. Consultar um profissional de saúde ou nutricionista pode ser útil para desenvolver um plano alimentar personalizado.", "Um treino intensivo é recomendado para a queima de calorias e melhoria da aptidão cardiovascular. Isso pode incluir atividades como corrida, treino intervalado de alta intensidade (HIIT) e exercícios aeróbicos.");
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

    private void displayImages(int dietaImageResId, int exercicioImageResId, String dietaDescricao, String exercicioDescricao) {
        imageViewDieta.setImageResource(dietaImageResId);
        imageViewExercicio.setImageResource(exercicioImageResId);

        TextView textViewDietaDescricao = findViewById(R.id.textViewDietaDescricao);
        TextView textViewExercicioDescricao = findViewById(R.id.textViewExercicioDescricao);

        textViewDietaDescricao.setText(dietaDescricao);
        textViewExercicioDescricao.setText(exercicioDescricao);
    }



}
