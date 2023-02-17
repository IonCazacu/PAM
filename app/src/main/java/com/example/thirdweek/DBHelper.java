package com.example.thirdweek;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    ArrayList<DBTable> tables;

    public DBHelper(Context context, ArrayList<DBTable> tables) {
        super(context, "myDBase", null, 1);
        this.tables = tables;
    }

    public String make_query(int index){
        String query = "create table " + this.tables.get(index).name + " (id integer primary key autoincrement,";

        for(int i = 0; i < this.tables.get(index).types.size(); i++){
            query += this.tables.get(index).names + " " + this.tables.get(index).types + ", ";
        }

        query += ");";
        return query;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;

        for(int i = 0; i < this.tables.size(); i++)
        {
            query = this.make_query(i);
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
