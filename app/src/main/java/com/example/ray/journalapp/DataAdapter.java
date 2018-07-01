package com.example.ray.journalapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ray.journalapp.database.DataEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{


    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<DataEntry> mDataEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the TaskAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public DataAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new DataViewHolder that holds the view for each task
     */
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.data_layout, parent, false);

        return new DataViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        // Determine the values of the wanted data
        DataEntry dataEntry = mDataEntries.get(position);
        String description = dataEntry.getDescription();
        int priority = dataEntry.getPriority();
        String updatedAt = dateFormat.format(dataEntry.getUpdatedAt());

        //Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        //String priorityString = "" + priority; // converts int to String
        //holder.priorityView.setText(priorityString);

        //GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        //int priorityColor = getPriorityColor(priority);
        //priorityCircle.setColor(priorityColor);
    }



    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mDataEntries == null) {
            return 0;
        }
        return mDataEntries.size();
    }

    public List<DataEntry> getData() {
        return mDataEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setData(List<DataEntry> dataEntries) {
        mDataEntries = dataEntries;
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;

        /**
         * Constructor for the DataViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public DataViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = itemView.findViewById(R.id.dataDescription);
            updatedAtView = itemView.findViewById(R.id.dataUpdatedAt);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mDataEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
