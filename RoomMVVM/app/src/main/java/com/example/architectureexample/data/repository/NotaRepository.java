package com.example.architectureexample.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.architectureexample.data.dao.NotaDao;
import com.example.architectureexample.data.database.NotaDatabase;
import com.example.architectureexample.model.Nota;

import java.util.List;

public class NotaRepository {
    private NotaDao notaDao;
    private LiveData<List<Nota>> allNotas;

    public NotaRepository(Application application){
        NotaDatabase notaDatabase = NotaDatabase.getInstance(application);
        notaDao = notaDatabase.notaDao();
        allNotas = notaDao.getAllNotas();
    }

    public void insert(Nota nota){
        new InsertNotaAsyncTask(this.notaDao).execute(nota);
    }

    public void update(Nota nota){
        new UpdateNotaAsyncTask(this.notaDao).execute(nota);
    }

    public void delete(Nota nota){
        new DeleteNotaAsyncTask(this.notaDao).execute(nota);;
    }

    public void deleteAllNotas() {
        new DeleteAllNotasAsyncTask(this.notaDao).execute();
    }

    public LiveData<List<Nota>> getAllNotas(){
        return allNotas;
    }

    private static class InsertNotaAsyncTask extends AsyncTask<Nota, Void, Void>{
        private NotaDao notaDao;
        private InsertNotaAsyncTask(NotaDao notaDao){
            this.notaDao = notaDao;
        }

        @Override
        protected Void doInBackground(Nota... notas) {
            notaDao.insert(notas[0]);
            return null;
        }
    }

    private static class UpdateNotaAsyncTask extends AsyncTask<Nota, Void, Void>{
        private NotaDao notaDao;
        private UpdateNotaAsyncTask(NotaDao notaDao){
            this.notaDao = notaDao;
        }

        @Override
        protected Void doInBackground(Nota... notas) {
            notaDao.update(notas[0]);
            return null;
        }
    }

    private static class DeleteNotaAsyncTask extends AsyncTask<Nota, Void, Void>{
        private NotaDao notaDao;
        private DeleteNotaAsyncTask(NotaDao notaDao){
            this.notaDao = notaDao;
        }

        @Override
        protected Void doInBackground(Nota... notas) {
            notaDao.delete(notas[0]);
            return null;
        }
    }

    private static class DeleteAllNotasAsyncTask extends AsyncTask<Void, Void, Void>{
        private NotaDao notaDao;
        private DeleteAllNotasAsyncTask(NotaDao notaDao){
            this.notaDao = notaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notaDao.deleteAllNotas();
            return null;
        }
    }
}
