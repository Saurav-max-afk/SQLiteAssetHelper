package com.aptron.sqliteassethelper.SQLitepackage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.aptron.sqliteassethelper.RecyclerPackage.DbModelClass;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class MyDbClass extends SQLiteAssetHelper {

    private static final String DATABASE_NAME="db2CopyCopyCopyCopy.db";
    private static final int DATABASE_VERSION=1;

    Context context;

    public MyDbClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    public ArrayList<DbModelClass> getAllData()
    {
        try {
            ArrayList<DbModelClass> objdbModelClassArrayList=new ArrayList<>();
            SQLiteDatabase objsqliteDatabase = getReadableDatabase();
            if (objsqliteDatabase!=null)
            {
                Cursor objCursor =objsqliteDatabase.rawQuery("select * from EnglishHindiDictionary1",null);
                if (objCursor.getCount()!=0)
                {
                    while (objCursor.moveToNext())
                    {
                        String textview1= objCursor.getString(0);
                        String textview2=objCursor.getString(1);

                        objdbModelClassArrayList.add(
                                new DbModelClass(
                                        textview1,textview2
                                )
                        );
                    }
                    return objdbModelClassArrayList;



                }else {
                    Toast.makeText(context, "No data is retrived", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }else {
                Toast.makeText(context, "DataBase is null", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch (Exception e){
            Toast.makeText(context, "getAllData:-"+e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }


}
