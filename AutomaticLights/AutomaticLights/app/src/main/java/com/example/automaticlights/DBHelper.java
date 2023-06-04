package com.example.automaticlights;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Userdata";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Userdata (username TEXT PRIMARY KEY, password TEXT)");
        populateUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Userdata");
        onCreate(db);
    }

    public boolean insertData(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        // Check if the user already exists in the table
        String query = "SELECT username FROM Userdata WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        if (exists) {
            db.close();
            return false; // User already exists, return false
        }

        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long result = db.insert("Userdata", null, cv);
        Log.e("DBHelper", "result: " + result);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean checkUserPass(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Userdata WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private void populateUsers(SQLiteDatabase db) {
        //db.delete("Userdata", null, null);

        String[][] users = {
                {"j@gmail.com", "senha1"},
                {"p@gmail.com", "senha2"},
                {"i@gmail.com", "senha3"}
        };

        for (String[] user : users) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", user[0]);
            contentValues.put("password", user[1]);
            long result = db.insert("Userdata", null, contentValues);
            if (result == -1) {
                Log.e("DBHelper", "Failed to insert user: " + user[0]);
            }
        }
    }
}
