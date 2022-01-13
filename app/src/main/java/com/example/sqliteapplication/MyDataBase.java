package com.example.sqliteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

public class MyDataBase extends SQLiteOpenHelper {
    public MyDataBase(Context context) {
        super(context, "name.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tableimage (name text, image blob);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists tableimage");
    }

    public boolean insertData(String username, byte[] img) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", username);
        contentValues.put("image", img);
        long ins = MyDB.insert("tableimage", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

    public String getName(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from tableimage where name = ?", new String[]{name});
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public Bitmap getImage(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from tableimage where name = ?", new String[]{name});
        cursor.moveToFirst();
        byte[] bitmap = cursor.getBlob(1);
        Bitmap image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        return image;
    }
}
