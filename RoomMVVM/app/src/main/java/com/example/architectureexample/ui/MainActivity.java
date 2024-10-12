package com.example.architectureexample.ui;

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

import com.example.architectureexample.model.Nota;
import com.example.architectureexample.ui.adapter.NotaAdapter;
import com.example.architectureexample.viewmodel.NotaViewModel;
import com.example.architectureexample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTA_REQUEST = 1;
    public static final int EDIT_NOTA_REQUEST = 2;

    private NotaViewModel notaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNota = findViewById(R.id.button_add_nota);
        buttonAddNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNotaActivity.class);
                startActivityForResult(intent, ADD_NOTA_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NotaAdapter adapter = new NotaAdapter();
        recyclerView.setAdapter(adapter);

        notaViewModel = new ViewModelProvider(this).get(NotaViewModel.class);
        notaViewModel.getAllNotas().observe(this, new Observer<List<Nota>>() {
            @Override
            public void onChanged(List<Nota> notas) {
                //Actualizo el RecyclerView
                adapter.setNotas(notas);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notaViewModel.delete(adapter.getNotaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Nota Eliminada", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NotaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                Intent intent = new Intent(MainActivity.this, AddEditNotaActivity.class);
                intent.putExtra(AddEditNotaActivity.EXTRA_TITULO, nota.getTitulo());
                intent.putExtra(AddEditNotaActivity.EXTRA_DESCRIPCION, nota.getDescripcion());
                intent.putExtra(AddEditNotaActivity.EXTRA_PRIORIDAD, nota.getPrioridad());
                intent.putExtra(AddEditNotaActivity.EXTRA_ID, nota.getId());
                startActivityForResult(intent, EDIT_NOTA_REQUEST);
            }
        });
        // model.getUiState().observe(this, uiState -> {
        //            // update UI
        //        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTA_REQUEST && resultCode==RESULT_OK){
            String titulo = data.getStringExtra(AddEditNotaActivity.EXTRA_TITULO).toString();
            String descripcion = data.getStringExtra(AddEditNotaActivity.EXTRA_DESCRIPCION).toString();
            int prioridad = data.getIntExtra(AddEditNotaActivity.EXTRA_PRIORIDAD, 1);

            Nota nota = new Nota(titulo, descripcion, prioridad);

            notaViewModel.insert(nota);

            Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTA_REQUEST && resultCode==RESULT_OK){
            int id = data.getIntExtra(AddEditNotaActivity.EXTRA_ID, -1);
            if (id == -1){
                Toast.makeText(this, "No de pudo actualizar", Toast.LENGTH_SHORT).show();
                return;
            }

            String titulo = data.getStringExtra(AddEditNotaActivity.EXTRA_TITULO).toString();
            String descripcion = data.getStringExtra(AddEditNotaActivity.EXTRA_DESCRIPCION).toString();
            int prioridad = data.getIntExtra(AddEditNotaActivity.EXTRA_PRIORIDAD, 1);

            Nota nota = new Nota(titulo, descripcion, prioridad);
            nota.setId(id);
            notaViewModel.update(nota);
            Toast.makeText(this, "Nota Actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nota no guardada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_notas){
            notaViewModel.deleteAllNotas();
            Toast.makeText(this, "Se eliminaron todas las Notas", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}