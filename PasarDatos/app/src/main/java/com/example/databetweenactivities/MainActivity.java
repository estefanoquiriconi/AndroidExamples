package com.example.databetweenactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.databetweenactivities.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> operationLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupWindowInsets();

        binding.btnAdd.setOnClickListener(this);
        binding.btnSub.setOnClickListener(this);

        setupActivityResultLauncher();
    }

    public void setupWindowInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void setupActivityResultLauncher(){
        operationLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                if(data != null){
                    int res = data.getIntExtra("res", 0);
                    String message = "El valor retornado es: " + res;
                    binding.tvResult.setText(message);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(validateInputs()){
            String operator = R.id.btnAdd == view.getId() ? "+" : "-";
            launchResultActivity(operator);
        }else{
            Toast.makeText(this, "Debes ingresar ambos operandos.", Toast.LENGTH_SHORT).show();
        }

    }

    private void launchResultActivity(String operator) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("operator", operator);
        intent.putExtra("ope1", Integer.parseInt(binding.etOpe1.getText().toString()));
        intent.putExtra("ope2", Integer.parseInt(binding.etOpe2.getText().toString()));
        operationLauncher.launch(intent);
    }

    public boolean validateInputs(){
        return isNotEmpty(binding.etOpe1) && isNotEmpty(binding.etOpe2);
    }

    public boolean isNotEmpty(EditText editText){
        return !editText.getText().toString().trim().isEmpty();
    }

}