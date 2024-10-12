package com.example.architectureexample.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.architectureexample.model.Nota;

import java.util.List;

@Dao
public interface NotaDao {
    @Insert
    void insert(Nota nota);

    @Update
    void update(Nota nota);

    @Delete
    void delete(Nota nota);

    @Query("DELETE FROM tabla_nota")

    void deleteAllNotas();
    @Query("SELECT * FROM tabla_nota ORDER BY prioridad DESC")

    LiveData<List<Nota>> getAllNotas();

}
