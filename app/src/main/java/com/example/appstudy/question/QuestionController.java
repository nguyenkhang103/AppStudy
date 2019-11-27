package com.example.appstudy.question;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class QuestionController {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private static QuestionController instance;

    public QuestionController(Context context) {
        dbHelper= new DBHelper(context);
    }
    public static QuestionController getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionController(context);
        }
        return instance;
    }

    /**
     * Open the databases connection.
     */
    public void open() {
        this.database = dbHelper.getWritableDatabase();
    }

    /**
     * Close the databases connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    //Lấy danh sách câu hỏi
    public ArrayList<Question> getQuestion(){
        ArrayList<Question> lsData= new ArrayList<Question>();
        Cursor cursor = database.rawQuery("SELECT * FROM 'tracnghiem' ",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question item;
            item= new Question(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),
                    cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7),cursor.getString(8),cursor.getString(9),"");
            lsData.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return lsData;
    }


    //Lấy danh sách câu hỏi
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public ArrayList<Question> getQuestion() {
//        ArrayList<Question> lsData = new ArrayList<Question>();
//
//
//        sqLiteDatabase = DBHelper.initDatabase(this,DATABASE_NAME);
//        Cursor cursor  = sqLiteDatabase.rawQuery("SELECT * FROM tracnghiem",null);
//        cursor.moveToFirst();
//
//        do {
//            Question item;
//            item = new Question(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8), cursor.getString(9), "");
//            lsData.add(item);
//        } while (cursor.moveToNext());
//
//        return lsData;
//    }



}

