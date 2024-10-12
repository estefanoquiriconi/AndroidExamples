package com.example.architectureexample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.architectureexample.data.repository.NotaRepository;
import com.example.architectureexample.model.Nota;

import java.util.List;

public class NotaViewModel extends AndroidViewModel {
    private NotaRepository repository;
    LiveData<List<Nota>> allNotas;

    public NotaViewModel(@NonNull Application application) {
        super(application);
        repository = new NotaRepository(application);
        allNotas = repository.getAllNotas();
    }

    public void insert(Nota nota){
        repository.insert(nota);
    }

    public void update(Nota nota){
        repository.update(nota);
    }
    public void delete(Nota nota){
        repository.delete(nota);
    }
    public void deleteAllNotas(){
        repository.deleteAllNotas();
    }

    public LiveData<List<Nota>> getAllNotas(){
        return allNotas;
    }
}
