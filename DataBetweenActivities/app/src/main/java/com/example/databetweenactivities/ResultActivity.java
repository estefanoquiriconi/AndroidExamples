package com.example.databetweenactivities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.databetweenactivities.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    private int res = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();

        if(intent != null){
            String operator = intent.getStringExtra("operator");
            int ope1 = intent.getIntExtra("ope1", 0);
            int ope2 = intent.getIntExtra("ope2", 0);

            if(operator != null){
                switch (operator){
                    case "+":
                        res = ope1 + ope2;
                        break;
                    case "-":
                        res = ope1 - ope2;
                        break;
                }
            }
            String message = "El resultado es: " + res;
            binding.textView.setText(message);
        }

        binding.btnReturn.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("res", res);
            setResult(RESULT_OK, result);
            finish();
        });

    }
}