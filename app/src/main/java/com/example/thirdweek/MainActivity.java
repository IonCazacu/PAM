package com.example.thirdweek;

import com.example.thirdweek.DBHelper;
import com.example.thirdweek.DBTable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {
    Button btnAdd, btnRead, btnClear;
    EditText txtinfo, etfromDB;
    DBHelper dbHelper;
    SQLiteDatabase db;

    String dbnames[] = {"table1"};
    ArrayList<String> types, names;
    ArrayList<DBTable> tables;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (this.checkExternalMedia()){
            this.readSDFile("CATEGORYS.txt", types, names);
            tables.add(new DBTable(dbnames[0], types, names));
        }

        dbHelper = new DBHelper(this, this.tables);
        db = dbHelper.getWritableDatabase();
    }

    private boolean checkExternalMedia() {
        boolean mExternalStorageAvailable = false;

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = true;
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
        }

        return mExternalStorageAvailable;
    }

    public void readSDFile(String FileName, ArrayList<String> types, ArrayList<String> names) {
        String row = "";

        try {
            File myFile = new File("/mnt/sdcard/Download/" + FileName);
            FileInputStream fIn = new FileInputStream(myFile);

            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            while ((row = myReader.readLine()) != null) {
                this.parse(row, types, names);
            }

            myReader.close();
        } catch (Exception e) {
            System.out.println("Error bitch!!!");
            return;
        }
    }

    public void parse(String line, ArrayList<String> types, ArrayList<String> names)
    {
        types.add(line.split("\t")[1]);
        names.add(line.split("\t")[0]);
    }


//    public void addInfo(View v) {
//        Log.d(LOG_TAG, "--- Insert in mytable: ---");
//        ContentValues cv = new ContentValues();
//        String name = txtinfo.getText().toString();
//        String[] ss=name.split(",");
//
//        cv.put("name", ss[0]);
//        cv.put("age", ss[1]);
//
//        long rowID = db.insert("mytable", null, cv);
//
//        Log.d(LOG_TAG, "row inserted, ID = " + rowID); }

//    public void readInfo(View v) {
//        Cursor c = db.query("mytable", null, null, null, null, null, null);
//
//        String res = "";
//        if (c.moveToFirst()) {
//            int idColIndex = c.getColumnIndex("id");
//            int nameColIndex = c.getColumnIndex("name");
//            int ageColIndex = c.getColumnIndex("age");
//            do {
//                res = c.getInt(idColIndex) + "," + c.getString(nameColIndex) + "," + c.getString(ageColIndex);
//            } while (c.moveToNext());
//
//            etfromDB.setText(res);
//        } else {
//            Log.d("FromDB", "0 rows"); etfromDB.setText("0 rows");
//        }
//        c.close(); }

//    public void clearInfo(View v) {
//        int clearCount = db.delete("mytable", null, null);
//        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
//    }
}