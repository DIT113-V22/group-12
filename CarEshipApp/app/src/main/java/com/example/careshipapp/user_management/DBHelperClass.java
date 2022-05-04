package com.example.careshipapp.user_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperClass extends SQLiteOpenHelper {

    private static final String DBNAME = "Users.db";
    private static final String TABLEUSERS = "Users";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String ADDRESS = "post address";
    private static final String ZIPCODE = "zip code";
    private static final String CREATEUSERSTABLE = "CREATE TABLE " + TABLEUSERS + " ("
            + USERNAME + " TEXT PRIMARY KEY AUTOINCREMENT, "
            + PASSWORD + " TEXT," + ADDRESS + "TEXT, " + ZIPCODE + "TEXT)";




    public DBHelperClass(Context context) {
        super(context, DBNAME, null, 1);//constructor for the users database.
    }
//another column "userType" that distinguishes customer from admin will be added later, if needed.

    @Override
    public void onCreate(SQLiteDatabase usersDB) {
        //Creating database table of users, where username is primary key, and password is user's password for login.
        usersDB.execSQL(CREATEUSERSTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase usersDB, int i, int i1) {
        //Method to upgrade the database.
        //usersDB.execSQL("DROP TABLE if exists TABLEUSERS");

    }


    public void insertData(String username, String password,String postAddress, String zipCode){


        //Method for inserting the data into users table.
        SQLiteDatabase usersDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();//Storing values.

        values.put(USERNAME, username);//Inserting username into values.
        values.put(PASSWORD, password);//Inserting password into values.

        values.put(ADDRESS, String.valueOf(postAddress));//Inserting address into values.
        values.put(ZIPCODE, String.valueOf(zipCode));//Inserting zipcode into values.



        usersDB.insert(TABLEUSERS, null, values);//Inserting the values into the table users.

        usersDB.close();

    }

    public Boolean usernameExistsCheck(String username){
        //Method that checks if user exists in the database.
        SQLiteDatabase usersDB = this.getWritableDatabase();
        Cursor cursor = usersDB.rawQuery("SELECT * FROM Users WHERE username = ?", new String[] {username});

        return cursor.getCount() > 0;

    }

    public Boolean userExistsCheck(String username, String password){
        //Method that checks if user exists in the database.
        SQLiteDatabase usersDB = this.getWritableDatabase();
        Cursor cursor = usersDB.rawQuery("SELECT * FROM Users WHERE username = ? and password = ?", new String[] {username, password});

        return cursor.getCount() > 0;

    }
 
    public void updatePassword(String username, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PASSWORD, password);
        database.update(TABLEUSERS, values, USERNAME+" = ?", new String[]{ username });
        database.close();

    }


}
