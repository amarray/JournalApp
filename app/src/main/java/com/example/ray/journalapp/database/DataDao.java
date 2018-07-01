package com.example.ray.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DataDao {
    @Query("SELECT * FROM data ORDER BY priority")
    LiveData<List<DataEntry>> loadAllData();

    @Insert
    void insertData(DataEntry dataEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateData(DataEntry dataEntry);

    @Delete
    void deleteData(DataEntry dataEntry);

    @Query("SELECT * FROM data WHERE id = :id")
    LiveData<DataEntry> loadDataById(int id);
}
