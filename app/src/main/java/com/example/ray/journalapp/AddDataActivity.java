package com.example.ray.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ray.journalapp.database.AppDatabase;
import com.example.ray.journalapp.database.DataEntry;

import java.util.Date;

public class AddDataActivity extends AppCompatActivity {
    // Extra for the task ID to be received in the intent
    public static final String EXTRA_DATA_ID = "extraDataId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_DATA_ID = "instanceDataId";

    //AppExecutors

    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_DATA_ID = -1;
    // Constant for logging
    private static final String TAG = AddDataActivity.class.getSimpleName();
    // Fields for views
    EditText mEditText;
    Button mButton;

    private int mDataId = DEFAULT_DATA_ID;

    // Member variable for the Database
    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_DATA_ID)) {
            mDataId = savedInstanceState.getInt(INSTANCE_DATA_ID, DEFAULT_DATA_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DATA_ID)) {
            mButton.setText(R.string.update_button);
            if (mDataId == DEFAULT_DATA_ID) {
                // populate the UI
                mDataId = intent.getIntExtra(EXTRA_DATA_ID, DEFAULT_DATA_ID);

                // COMPLETED (9) Remove the logging and the call to loadTaskById, this is done in the ViewModel now
                // COMPLETED (10) Declare a AddTaskViewModelFactory using mDb and mTaskId
                AddDataViewModelFactory factory = new AddDataViewModelFactory(mDb, mDataId);
                // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddTaskViewModel
                final AddDataViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddDataViewModel.class);

                // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getData().observe(this, new Observer<DataEntry>() {
                    @Override
                    public void onChanged(@Nullable DataEntry dataEntry) {
                        viewModel.getData().removeObserver(this);
                        populateUI(dataEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_DATA_ID, mDataId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mEditText = findViewById(R.id.editTextDataDescription);


        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param data the dataEntry to populate the UI
     */
    private void populateUI(DataEntry data) {
        if (data == null) {
            return;
        }

        mEditText.setText(data.getDescription());
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String description = mEditText.getText().toString();
        int priority = 1;
        Date date = new Date();

        final DataEntry data = new DataEntry(description, priority, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mDataId == DEFAULT_DATA_ID) {
                    // insert new data
                    mDb.dataDao().insertData(data);
                } else {
                    //update data
                    data.setId(mDataId);
                    mDb.dataDao().updateData(data);
                }
                finish();
            }
        });
    }


}
