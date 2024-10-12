package com.example.architectureexample.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.architectureexample.data.dao.NotaDao;
import com.example.architectureexample.model.Nota;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Nota.class}, version = 1)
public abstract class NotaDatabase extends RoomDatabase {
    private static NotaDatabase instance;

    public abstract NotaDao notaDao();

    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static synchronized NotaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NotaDatabase.class, "nota_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateDatabase(instance);
        }
    };

    private static void populateDatabase(NotaDatabase db) {
        executor.execute(() -> {
            NotaDao notaDao = db.notaDao();
            notaDao.insert(new Nota("Tile 1", "Description 1", 5));
            notaDao.insert(new Nota("Tile 2", "Description 2", 2));
            notaDao.insert(new Nota("Tile 3", "Description 3", 7));
        });
    }
}