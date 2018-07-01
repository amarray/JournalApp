package com.example.ray.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ray.journalapp.database.AppDatabase;
import com.example.ray.journalapp.database.DataEntry;

public class AddDataViewModel extends ViewModel {
    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private LiveData<DataEntry> data;

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public AddDataViewModel(AppDatabase database, int dataId) {
        data = database.dataDao().loadDataById(dataId);
    }

    // COMPLETED (7) Create a getter for the data variable
    public LiveData<DataEntry> getData() {
        return data;
    }
}
