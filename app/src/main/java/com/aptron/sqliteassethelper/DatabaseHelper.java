package com.aptron.sqliteassethelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student9.db";
    public static final String TABLE_NAME = "Student_table1";
    public static final String TABLE_NAME1 = "Student_table2";
    public static final String TABLE_NAME2 = "Student_table3";



    public static final String COL_1 = "ID";
    public static final String COL_2 = "text1";
    public static final String COL_3 = "text2";
    public static final String COL_4 = "text3";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,text1 TEXT  ,text2 TEXT)");
        db.execSQL("create table " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,text1 TEXT  ,text2 TEXT)");
        db.execSQL("create table " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT ,text1 TEXT  ,text2 TEXT ,text3 TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);

        onCreate(db);
    }

    public boolean insertIntoTheDatabase(String text_item1, String text_item2) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_2, text_item1);
        cv.put(COL_3, text_item2);
         db.insert(TABLE_NAME, null, cv);


            return true;

        }

    public boolean insertIntoTheDatabase1(String text_item1, String text_item2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        cv1.put(COL_2, text_item1);
        cv1.put(COL_3, text_item2);
        db.insert(TABLE_NAME1, null, cv1);
        return true;

    }
    public boolean insertIntoTheDatabase2(String text_item1, String text_item2,String text_item3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv2 = new ContentValues();
        cv2.put(COL_2, text_item1);
        cv2.put(COL_3, text_item2);
        cv2.put(COL_4, text_item3);
        db.insert(TABLE_NAME2, null, cv2);


        return true;

    }


    public Cursor read_all_data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_NAME, null);
        return cur;
    }

    public Cursor read_recent_data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur1 = db.rawQuery("select * from " + TABLE_NAME1, null);
        return cur1;
    }

    public Cursor read_feedback_data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur2 = db.rawQuery("select * from " + TABLE_NAME2, null);
        return cur2;
    }



    public void DeleteData(String value1, String value2) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "text1 = ? or text2 = ?", new String[]{value1, value2});

    }

    public void DeleteRecentData(String value1, String value2) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME1, "text1 = ? or text2 = ?", new String[]{value1, value2});

    }
    public Integer DeleteFeedbackData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, "ID = ?", new String[]{id});

    }
    public boolean updateFeedbackData(String id, String name, String word,String query){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv2 = new ContentValues();
        cv2.put(COL_1,id);
        cv2.put(COL_2, name);
        cv2.put(COL_3, word);
        cv2.put(COL_4, query);

        db.update(TABLE_NAME2,cv2,"ID = ?",new String[]{id});
        return true;

    }

}

