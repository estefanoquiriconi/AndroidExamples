package com.example.architectureexample.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.architectureexample.R;
import com.example.architectureexample.databinding.ActivityAddNotaBinding;

public class AddEditNotaActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.architectureexample.EXTRA_ID";
    public static final String EXTRA_TITULO = "com.example.architectureexample.EXTRA_TITULO";
    public static final String EXTRA_DESCRIPCION = "com.example.architectureexample.EXTRA_DESCRIPCION";
    public static final String EXTRA_PRIORIDAD = "com.example.architectureexample.EXTRA_PRIORIDAD";


    ActivityAddNotaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.numberPickerPrioridad.setMinValue(1);
        binding.numberPickerPrioridad.setMaxValue(10);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Editar Nota");
            binding.editTextTitulo.setText(intent.getStringExtra(EXTRA_TITULO));
            binding.editTextDescripcion.setText(intent.getStringExtra(EXTRA_DESCRIPCION));
            binding.numberPickerPrioridad.setValue(intent.getIntExtra(EXTRA_PRIORIDAD, 1));

        } else {
            setTitle("Agregar Nota");
        }
    }

    private void save_nota() {
        String titulo = binding.editTextTitulo.getText().toString();
        String descripcion = binding.editTextDescripcion.getText().toString();
        int prioridad = binding.numberPickerPrioridad.getValue();

        if (titulo.trim().isEmpty()) {
            binding.editTextTitulo.requestFocus();
            binding.editTextTitulo.setError("Por favor ingresa un título.");
            Toast.makeText(this, "Por favor ingresa un título", Toast.LENGTH_SHORT).show();
            return;
        }

        if (descripcion.trim().isEmpty()) {
            binding.editTextDescripcion.requestFocus();
            binding.editTextDescripcion.setError("Por favor ingresa una descripción.");
            Toast.makeText(this, "Por favor ingresa una descripción", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITULO, titulo);
        data.putExtra(EXTRA_DESCRIPCION, descripcion);
        data.putExtra(EXTRA_PRIORIDAD, prioridad);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_nota_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.guardar_nota) {
            save_nota();
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}