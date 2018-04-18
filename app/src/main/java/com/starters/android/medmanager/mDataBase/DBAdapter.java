package com.starters.android.medmanager.mDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Frederick
 */
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new DBHelper(c);
    }

    /**
     * opens the database for a writable operation
     */
    public void openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {

        }
    }

    /**
     * closes the database
     */
    public void closeDB()
    {
        try
        {
            helper.close();
        }catch (SQLException e)
        {

        }
    }


    /**
     * @param name medication name
     * @param desc medication description
     * @param interval the frequency or interval which the medication should be taken. eg(3 times a day).
     * @param date the starting and ending date for the medication
     * @return saves data to database
     */
    public boolean add(String name,String desc,String interval,String date)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.NAME,name);
            cv.put(Constants.DESC,desc);
            cv.put(Constants.INTERVAL,interval);
            cv.put(Constants.DATE,date);

            long result=db.insert(Constants.TB_NAME,Constants.ROW_ID,cv);
            if(result>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * @return retrieves data from the database
     */
    public Cursor retrieve()
    {
        String[] columns={Constants.ROW_ID,Constants.NAME,Constants.DESC,Constants.INTERVAL,Constants.DATE};

        Cursor c=db.query(Constants.TB_NAME,columns,null,null,null,null,null);
        return c;
    }
    public Cursor  getMedicationListByKeyword(String search) {
        //Open connection to read only
        //SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  id as " +
                Constants.ROW_ID + "," +
                Constants.NAME + "," +
                Constants.DESC + "," +
                Constants.INTERVAL + "," +
                Constants.DATE +
                " FROM " + Constants.TB_NAME +
                " WHERE " +  Constants.NAME + " LIKE '%" +search + "%' "
                ;
        /*Cursor c;
        c=db.rawQuery("SELECT * FROM "+ Constants.TB_NAME + " WHERE "
                + Constants.ROW_ID + " = " + Constants.ROW_ID + " AND " + DB_NAME.Title +
                " LIKE '%"+search+"%'");*/

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }

    /**
     * @param newName new medication name
     * @param newDesc new medication description
     * @param newInterval new frequency or interval which the medication should be taken. eg(3 times a day).
     * @param newDate the starting and ending date for the medication
     * @param id the id for each row in the database
     * @return updates medication data
     */
    public boolean update(String newName,String newDesc,String newInterval,String newDate,int id)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.NAME,newName);
            cv.put(Constants.DESC,newDesc);
            cv.put(Constants.INTERVAL,newInterval);
            cv.put(Constants.DATE,newDate);


            int result=db.update(Constants.TB_NAME,cv, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
             e.printStackTrace();
        }

        return false;

    }

    //DELETE/REMOVE

    /**
     * @param id id for the data item
     * @return deletes/removes data item from database using its id
     */
    public boolean delete(int id)
    {
        try
        {
            int result=db.delete(Constants.TB_NAME,Constants.ROW_ID+" =?",new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }


}












