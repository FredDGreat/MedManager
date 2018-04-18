package com.starters.android.medmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // when the view will be created for first time,
        // we need to tell the adapters, how each item will look
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.list_item_home_fragment, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // here we are setting our data
        // that means, take the data from the cursor and put it in views

        TextView mMedName = (TextView) view.findViewById(R.id.medName);
        mMedName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        TextView mDesc = (TextView) view.findViewById(R.id.medDesc);
        mDesc.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
        TextView mInterval = (TextView) view.findViewById(R.id.notificationLabel);
        mInterval.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
        TextView mDate = (TextView) view.findViewById(R.id.startNEndDate);
        mDate.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));
    }
}
