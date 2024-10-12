package com.example.architectureexample.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.architectureexample.databinding.ActivityMainBinding;
import com.example.architectureexample.model.Nota;
import com.example.architectureexample.ui.adapter.NotaAdapter;
import com.example.architectureexample.viewmodel.NotaViewModel;
import com.example.architectureexample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotaViewModel notaViewModel;
    private final NotaAdapter adapter = new NotaAdapter();
    private ActivityMainBinding binding;

    private final ActivityResultLauncher<Intent> addNotaLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String titulo = result.getData().getStringExtra(AddEditNotaActivity.EXTRA_TITULO);
                    String descripcion = result.getData().getStringExtra(AddEditNotaActivity.EXTRA_DESCRIPCION);
                    int prioridad = result.getData().getIntExtra(AddEditNotaActivity.EXTRA_PRIORIDAD, 1);

                    notaViewModel.insert(new Nota(titulo, descripcion, prioridad));
                    Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Nota no guardada", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> editNotaLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    int id = result.getData().getIntExtra(AddEditNotaActivity.EXTRA_ID, -1);
                    if (id == -1) {
                        Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String titulo = result.getData().getStringExtra(AddEditNotaActivity.EXTRA_TITULO);
                    String descripcion = result.getData().getStringExtra(AddEditNotaActivity.EXTRA_DESCRIPCION);
                    int prioridad = result.getData().getIntExtra(AddEditNotaActivity.EXTRA_PRIORIDAD, 1);

                    Nota nota = new Nota(titulo, descripcion, prioridad);
                    nota.setId(id);
                    notaViewModel.update(nota);
                    Toast.makeText(this, "Nota Actualizada", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddNota.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEditNotaActivity.class);
            addNotaLauncher.launch(intent);
        });

        setupRecyclerView();

        notaViewModel = new ViewModelProvider(this).get(NotaViewModel.class);
        notaViewModel.getAllNotas().observe(this, adapter::setNotas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_notas) {
            notaViewModel.deleteAllNotas();
            Toast.makeText(this, "Se eliminaron todas las Notas", Toast.LENGTH_SHORT).show();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    public void setupRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notaViewModel.delete(adapter.getNotaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Nota Eliminada", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerView);

        adapter.setOnItemClickListener(nota -> {
            Intent intent = new Intent(MainActivity.this, AddEditNotaActivity.class);
            intent.putExtra(AddEditNotaActivity.EXTRA_TITULO, nota.getTitulo());
            intent.putExtra(AddEditNotaActivity.EXTRA_DESCRIPCION, nota.getDescripcion());
            intent.putExtra(AddEditNotaActivity.EXTRA_PRIORIDAD, nota.getPrioridad());
            intent.putExtra(AddEditNotaActivity.EXTRA_ID, nota.getId());
            editNotaLauncher.launch(intent);
        });
    }
}