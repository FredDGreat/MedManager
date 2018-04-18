package com.starters.android.medmanager.mDataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Frederick
 */
public class DBHelperForRegAndLogin extends SQLiteOpenHelper {

    public DBHelperForRegAndLogin(Context context) {
        super(context, Constants.TB_NAME_FOR_REG_AND_LOGIN, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(Constants.CREATE_TB_FOR_REG_AND_LOGIN);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL(Constants.DROP_TB_FOR_REG_AND_LOGIN);
        onCreate(db);
    }
}
