package com.starters.android.medmanager.mDataBase;

/**
 * Created by Frederick
 */
public class Constants {
    //COLUMNS
    static final String ROW_ID="id";
    static final String NAME="name";
    static final String DESC="desc";
    static final String INTERVAL="interval";
    static final String DATE="date";
    //COLUMNS for registration and login Table
    static final String ROW_ID_FOR_REG_AND_LOGIN="id";
    static final String FULL_NAME="full_name";
    static final String EMAIL="email";
    static final String PASSWORD="password";
    static final String MOBLIE="mobile";


    //DB PROPERTIES
    static final String DB_NAME="hh_DB";
    static final String TB_NAME="med_TB";
    static final String TB_NAME_FOR_REG_AND_LOGIN="med_reg_and_login_TB";
    static final int DB_VERSION=1;

    //CREATE TB STMT
    static final String CREATE_TB="CREATE TABLE "+TB_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,desc TEXT NOT NULL, interval TEXT NOT NULL,date TEXT NOT NULL);";

    //CREATE TB STMT
    static final String CREATE_TB_FOR_REG_AND_LOGIN="CREATE TABLE "+TB_NAME_FOR_REG_AND_LOGIN+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "full_name TEXT NOT NULL,email TEXT NOT NULL, password TEXT NOT NULL,mobile TEXT NOT NULL);";


    //DROP TB STMT
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;
    //DROP TB STMT
    static final String DROP_TB_FOR_REG_AND_LOGIN="DROP TABLE IF EXISTS "+TB_NAME_FOR_REG_AND_LOGIN;
}
