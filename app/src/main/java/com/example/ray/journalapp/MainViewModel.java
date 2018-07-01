package com.example.ray.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.ray.journalapp.database.AppDatabase;
import com.example.ray.journalapp.database.DataEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<DataEntry>> data;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        data = database.dataDao().loadAllData();
    }

    public LiveData<List<DataEntry>> getData() {
        return data;
    }
}

