package com.estefanoquiriconi.miproyecto002;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    private RadioButton rb1, rb2;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        tv1 = findViewById(R.id.tv1);
    }

    public void operar(View view){
        if(et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty()){
            Toast.makeText(this, "¡Debes ingresar ambos operandos!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!rb1.isChecked() && !rb2.isChecked()){
            Toast.makeText(this, "¡Debes seleccionar una operación!", Toast.LENGTH_SHORT).show();
            return;
        }
        String s1 = et1.getText().toString();
        String s2 = et2.getText().toString();
        int valorUno = Integer.parseInt(s1);
        int valorDos = Integer.parseInt(s2);
        if(rb1.isChecked()){
            int suma = valorUno + valorDos;
            tv1.setText("La suma es: " + suma);
        }else if(rb2.isChecked()){
            int resta = valorUno - valorDos;
            tv1.setText("La resta es: " + resta);
        }
    }
}