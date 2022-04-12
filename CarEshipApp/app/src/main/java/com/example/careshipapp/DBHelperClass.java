package com.example.careshipapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperClass extends SQLiteOpenHelper {

    public static final String DBNAME = "Users.db";

    public DBHelperClass(Context context) {
        super(context, DBNAME, null, 1);//constructor for the users database.
    }
//another column "userType" that distinguishes customer from admin will be added later, if needed.

    @Override
    public void onCreate(SQLiteDatabase usersDB) {
        //Creating database table of users, where username is primary key, and password is user's password for login.
        usersDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase usersDB, int i, int i1) {
        //Method to upgrade the database.
        usersDB.execSQL("drop Table if exists users");

    }

    public Boolean insertData(String username, String password){
        //Method for inserting the data into users table.
        SQLiteDatabase usersDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();//Storing values.

        values.put("username", username);//Inserting username into values.
        values.put("password", password);//Inserting password into values.

        long outcome = usersDB.insert("users", null, values);//Inserting the values into the table users.

        return outcome != -1; //Return false if outcome is -1, and true if it is not -1.
    }

    public Boolean userExistsCheck(String username, String password){
        //Method that checks if user exists in the database.
        SQLiteDatabase usersDB = this.getWritableDatabase();
        Cursor cursor = usersDB.rawQuery("SELECT * FROM users WHERE username = ? and password = ?", new String[] {username, password});

        return cursor.getCount() > 0;

    }

    public Boolean adminExistsCheck(String username){
        //Method that checks if admin exists in the database.
        SQLiteDatabase usersDB = this.getWritableDatabase();
        Cursor cursor = usersDB.rawQuery("SELECT * FROM users WHERE username LIKE 'admin%' and '%carEship%'", new String[] {username});

        return cursor.getCount() > 0;
    }
}
