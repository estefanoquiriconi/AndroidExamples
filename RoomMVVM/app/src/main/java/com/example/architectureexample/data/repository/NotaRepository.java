package com.example.architectureexample.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.architectureexample.data.dao.NotaDao;
import com.example.architectureexample.data.database.NotaDatabase;
import com.example.architectureexample.model.Nota;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotaRepository {
    private final NotaDao notaDao;
    private final LiveData<List<Nota>> allNotas;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public NotaRepository(Application application) {
        NotaDatabase notaDatabase = NotaDatabase.getInstance(application);
        notaDao = notaDatabase.notaDao();
        allNotas = notaDao.getAllNotas();
    }

    public LiveData<List<Nota>> getAllNotas() {
        return allNotas;
    }

    public void insert(Nota nota) {
        executor.execute(() -> notaDao.insert(nota));
    }

    public void update(Nota nota) {
        executor.execute(() -> notaDao.update(nota));
    }

    public void delete(Nota nota) {
        executor.execute(() -> notaDao.delete(nota));
    }

    public void deleteAllNotas() {
        executor.execute(notaDao::deleteAllNotas);
    }
}
