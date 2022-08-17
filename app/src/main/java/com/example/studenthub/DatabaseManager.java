package com.example.studenthub;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;


public class DatabaseManager {

    public static final String DB_NAME = "StudentHub";
    public static final String TABLE_FRIEND = "Friend";
    public static final String TABLE_TASK = "Task";
    public static final String TABLE_EXAM = "Exam";
    public static final int DB_VERSION = 7;
    private static final String CREATE_TABLE_FRIEND = "CREATE TABLE " + TABLE_FRIEND +
            " (SID NUMBER PRIMARY KEY, FName TEXT, LName TEXT, " +
            "Course NUMBER, Gender TEXT, Age NUMBER, Address Text, Img Text);";


    private static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_TASK +
            " (TID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TName TEXT, Location TEXT, Status TEXT);";


    private static final String CREATE_TABLE_EXAM= "CREATE TABLE " + TABLE_EXAM +
            " (EID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, SID NUMBER, EName TEXT, Date TEXT, Location TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addStudent(long SID, String fName,
                          String lName,  int c, int age,
                              String g, String address) {
        synchronized(this.db) {

            ContentValues newStu = new ContentValues();
            newStu.put("SID", SID);
            newStu.put("FName", fName);
            newStu.put("LName", lName);
            newStu.put("Course", c);
            newStu.put("Age", age);
            newStu.put("Gender", g);
            newStu.put("Address",address);

            try {
                db.insertOrThrow(TABLE_FRIEND, null, newStu);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public boolean addTask(String TName, String Location, String Status) {
        synchronized(this.db) {

            ContentValues newFriend = new ContentValues();
            newFriend.put("TName", TName);
            newFriend.put("Location", Location);
            newFriend.put("Status", Status);

            try {
                db.insertOrThrow(TABLE_TASK, null, newFriend);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public boolean addExam(long SID, String ename, String date, String location) {
        synchronized(this.db) {

            ContentValues newExam = new ContentValues();
            newExam.put("SID", SID);
            newExam.put("EName", ename);
            newExam.put("Date", date);
            newExam.put("Location", location);

            try {
                db.insertOrThrow(TABLE_EXAM, null, newExam);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }


    public ArrayList<String> retrieveStudent() {
        ArrayList<String> studentRows = new ArrayList<String>();
        String[] columns = new String[] {"SID", "FName", "LName","Course",
                "Gender", "Age", "Address"};
        Cursor cursor = db.query(TABLE_FRIEND, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            studentRows.add(cursor.getString(0) + ", " +
                    cursor.getString(1) + ", " +
                    cursor.getString(2) + ", " +
                    cursor.getString(3) + ", " +
                    cursor.getString(4) + ", " +
                    cursor.getString(5) + "\n" +
                    cursor.getString(6));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }

    public ArrayList<String> selectStudent(String id) {
        ArrayList<String> studentRows = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_FRIEND +" WHERE SID = "+id, null);
        if (cursor.moveToFirst()) { //https://stackoverflow.com/a/36569631/16572065
            for (int i=0; i<8; i++)
                studentRows.add(cursor.getString(i));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return studentRows;
    }

    public boolean deleteStudent(String id){
        //
        try {
            db.execSQL("DELETE FROM "+ TABLE_FRIEND +" WHERE SID = "+id);
            //db.delete(TABLE_FRIEND, "SID = ?",new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("Error in deleting row", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateStudent(long SID, String fName,
                              String lName,  int c, int age,
                              String g, String address, String img) {
        synchronized(this.db) {

            ContentValues newStu = new ContentValues();
            newStu.put("SID", SID);
            newStu.put("FName", fName);
            newStu.put("LName", lName);
            newStu.put("Course", c);
            newStu.put("Age", age);
            newStu.put("Gender", g);
            newStu.put("Address",address);
            newStu.put("Img",img);

            try {
                db.update(TABLE_FRIEND, newStu, "SID = ?", new String[]{String.valueOf(SID)} );
            } catch (Exception e) {
                Log.e("Error in updating row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public ArrayList<String> retrieveTask(String stat) {
        ArrayList<String> taskRows = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_TASK+ " WHERE Status = \""+stat+"\"", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            taskRows.add(cursor.getString(0) + "| " +
                    cursor.getString(1) + ", " +
                    cursor.getString(2) + ", " +
                    cursor.getString(3));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return taskRows;
    }

    public ArrayList<String> selectTask(String id) {
        ArrayList<String> taskRows = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_TASK +" WHERE TID = "+id, null);
        if (cursor.moveToFirst()) { //https://stackoverflow.com/a/36569631/16572065
            for (int i=0; i<4; i++)
                taskRows.add(cursor.getString(i));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return taskRows;
    }
    public boolean deleteTask(String id){
        //
        try {
            db.execSQL("DELETE FROM "+ TABLE_TASK +" WHERE TID = "+id);
        } catch (Exception e) {
            Log.e("Error in deleting row", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateTask(String TID, String tname,
                                 String location, String status) {
        synchronized(this.db) {

            ContentValues newTask = new ContentValues();
            newTask.put("TName", tname);
            newTask.put("Location", location);
            newTask.put("Status", status);

            try {
                db.update(TABLE_TASK, newTask, "TID = ?", new String[]{TID} );
            } catch (Exception e) {
                Log.e("Error in updating row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
    public ArrayList<String> retrieveExamPast(String SID) {
        ArrayList<String> taskRows = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_EXAM+ " WHERE Date < datetime('now','localtime') AND SID = "+SID, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            taskRows.add(cursor.getString(2) + "| " +
                    cursor.getString(3) + ", " +
                    cursor.getString(4));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return taskRows;
    }
    public ArrayList<String> retrieveExamNow(String SID) {
        ArrayList<String> taskRows = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_EXAM+ " WHERE Date >= datetime('now','localtime') AND SID = "+SID, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            taskRows.add(cursor.getString(2) + "| " +
                    cursor.getString(3) + ", " +
                    cursor.getString(4));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return taskRows;
    }
    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_FRIEND);
            db.execSQL(CREATE_TABLE_TASK);
            db.execSQL(CREATE_TABLE_EXAM);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("StudentInfo Table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);
            onCreate(db);
        }
    }
}
