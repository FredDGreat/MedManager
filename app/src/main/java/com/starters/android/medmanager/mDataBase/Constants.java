package com.starters.android.medmanager.mDataBase;

/**
 * Created by Frederick
 */
public class Constants {
    //Monthly medication intake counter
    public static final String MONTHLY_COUNTER="_counter";
    //COLUMNS
    public static final String ROW_ID="id";
    public static final String NAME="name";
    public static final String DESC="desc";
    public static final String INTERVAL="interval";
    public static final String DATE="date";
    //COLUMNS for registration and login Table
    public static final String ROW_ID_FOR_REG_AND_LOGIN="id";
    public static final String FULL_NAME="full_name";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    public static final String MOBLIE="mobile";


    //DB PROPERTIES
    public static final String DB_NAME="hh_DB";
    public static final String TB_NAME="med_TB";
    public static final String TB_NAME_FOR_REG_AND_LOGIN="med_reg_and_login_TB";
    public static final int DB_VERSION=1;

    //CREATE TB STMT
    public static final String CREATE_TB="CREATE TABLE "+TB_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,desc TEXT NOT NULL, interval TEXT NOT NULL,date TEXT NOT NULL);";

    //CREATE TB STMT
    public static final String CREATE_TB_FOR_REG_AND_LOGIN="CREATE TABLE "+TB_NAME_FOR_REG_AND_LOGIN+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "full_name TEXT NOT NULL,email TEXT NOT NULL, password TEXT NOT NULL,mobile TEXT NOT NULL);";


    //DROP TB STMT
    public static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;
    //DROP TB STMT
    public static final String DROP_TB_FOR_REG_AND_LOGIN="DROP TABLE IF EXISTS "+TB_NAME_FOR_REG_AND_LOGIN;
}
