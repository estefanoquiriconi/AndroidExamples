package com.example.archivos;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.archivos.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private static final String ERROR_NO_FILENAME = "Por favor ingresa un nombre para el archivo!";
    private static final String ERROR_NO_CONTENT = "Por favor ingresa contenido para el archivo!";
    private static final String SUCCESS_SAVE = "Los datos fueron guardados!";
    private static final String ERROR_SAVE = "No se pudo crear el archivo!";
    private static final String ERROR_FILE_NOT_FOUND = "No existe el archivo!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnGuardar.setOnClickListener(v -> {
            if (isFilenameEmpty() || isContentEmpty()) return;

            String filename = binding.etNombreArchivo.getText().toString();
            String content = binding.tmContenidoArchivo.getText().toString();

            try (OutputStreamWriter fileWriter = new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))) {
                fileWriter.write(content);
                Toast.makeText(this, SUCCESS_SAVE, Toast.LENGTH_SHORT).show();
                clearInputs();
            } catch (IOException e) {
                Toast.makeText(this, ERROR_SAVE, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnRecuperar.setOnClickListener(v -> {
            if (isFilenameEmpty()) return;

            String filename = binding.etNombreArchivo.getText().toString();
            try (InputStreamReader fileReader = new InputStreamReader(openFileInput(filename));
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                StringBuilder content = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                binding.tmContenidoArchivo.setText(content.toString());
            } catch (IOException e) {
                Toast.makeText(this, ERROR_FILE_NOT_FOUND, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInputs() {
        binding.etNombreArchivo.setText("");
        binding.tmContenidoArchivo.setText("");
    }

    private boolean isFilenameEmpty() {
        return isFieldEmpty(binding.etNombreArchivo, ERROR_NO_FILENAME);
    }

    private boolean isContentEmpty() {
        return isFieldEmpty(binding.tmContenidoArchivo, ERROR_NO_CONTENT);
    }

    private boolean isFieldEmpty(EditText editText, String errorMessage) {
        if (editText.getText().toString().isEmpty()) {
            editText.requestFocus();
            editText.setError(errorMessage);
            return true;
        }
        return false;
    }
}