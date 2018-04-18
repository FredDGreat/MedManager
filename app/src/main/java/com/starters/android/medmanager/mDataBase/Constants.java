package com.starters.android.medmanager.mDataBase;

/**
 * Created by Oclemmy on 5/5/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String NAME="name";
    static final String DESC="desc";
    static final String INTERVAL="interval";
    static final String DATE="date";


    //DB PROPERTIES
    static final String DB_NAME="hh_DB";
    static final String TB_NAME="med_TB";
    static final int DB_VERSION=1;

    //CREATE TB STMT
    static final String CREATE_TB="CREATE TABLE "+TB_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,desc TEXT NOT NULL, interval TEXT NOT NULL,date TEXT NOT NULL);";


    //DROP TB STMT
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;
}
