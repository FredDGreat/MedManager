package com.starters.android.medmanager.mDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Frederick
 */
public class DBAdapterRegAndLogin {

    Context c;
    SQLiteDatabase db;
    DBHelperForRegAndLogin helper;

    public DBAdapterRegAndLogin(Context c) {
        this.c = c;
        helper=new DBHelperForRegAndLogin(c);
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
     *
     * @param fullName user's full name
     * @param email user's email
     * @param password user's password
     * @param mobile user's mobile number
     * @return adds user's registration credentials to database
     */
    public boolean add(String fullName,String email,String password,String mobile)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.FULL_NAME,fullName);
            cv.put(Constants.EMAIL,email);
            cv.put(Constants.PASSWORD,password);
            cv.put(Constants.MOBLIE,mobile);

            long result=db.insert(Constants.TB_NAME_FOR_REG_AND_LOGIN,Constants.ROW_ID_FOR_REG_AND_LOGIN,cv);
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
        //String[] columns={Constants.ROW_ID_FOR_REG_AND_LOGIN,Constants.FULL_NAME,Constants.EMAIL,Constants.PASSWORD,Constants.MOBLIE};

        Cursor c= db.rawQuery("SELECT * FROM "+Constants.TB_NAME_FOR_REG_AND_LOGIN, null);
        return c;
    }

    /**
     * @param newName new full name
     * @param email new user email
     * @param password new user password
     * @param mobile new user mobile number
     * @param id the id for each row in the database
     * @return updates user registration data
     */
    public boolean update(String newName,String email,String password,String mobile,int id)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.FULL_NAME,newName);
            cv.put(Constants.EMAIL,email);
            cv.put(Constants.PASSWORD,password);
            cv.put(Constants.MOBLIE,mobile);


            int result=db.update(Constants.TB_NAME_FOR_REG_AND_LOGIN,cv, Constants.ROW_ID_FOR_REG_AND_LOGIN
                    + " =?", new String[]{String.valueOf(id)});
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












