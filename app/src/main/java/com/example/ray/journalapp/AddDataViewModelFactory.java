package com.example.ray.journalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.ray.journalapp.database.AppDatabase;

public class AddDataViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    // COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private final AppDatabase mDb;
    private final int mDataId;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public AddDataViewModelFactory(AppDatabase database, int dataId) {
        mDb = database;
        mDataId = dataId;
    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddDataViewModel(mDb, mDataId);
    }
}
