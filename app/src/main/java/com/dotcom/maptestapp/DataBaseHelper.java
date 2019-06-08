package com.dotcom.maptestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {



    public DataBaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE login ( name text, email text primary key, pass text )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists login" );

    }
    public boolean insert(String name,String email,String pass){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("pass",pass);
        long ins =db.insert("login",null,contentValues);
        if (ins==-1)return false;
        else return true;
    }
    public boolean checkMail(String email){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From login where email=?",new String[]{email});

        if (cursor.getCount()>0) return false;
        else return true;
    }
    public boolean emaillogin(String email, String passward){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From login where email=? and pass=?",new String[]{email,passward});
        if (cursor.getCount()>0) return true;
        else return false;
    }
    public boolean profiledata(String name, String email){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * From login where name=? and email=?",new String[]{name,email});
        if (cursor.getCount()>0) return true;
        else return false;
    }
}
