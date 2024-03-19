package com.example.tp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_STUDENT = "student";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRENOM = "prenom";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_MATRICULE = "matricule";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRENOM + " TEXT,"
                + COLUMN_NOM + " TEXT,"
                + COLUMN_MATRICULE + " TEXT" + ")";
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public void addStudent(student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRENOM, student.getPrenom());
        values.put(COLUMN_NOM, student.getNom());
        values.put(COLUMN_MATRICULE, student.getMatricule());
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    public student getStudent(String matricule) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, new String[]{COLUMN_ID,
                        COLUMN_PRENOM, COLUMN_NOM, COLUMN_MATRICULE}, COLUMN_MATRICULE + "=?",
                new String[]{matricule}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        student student = new student();
        student.setId(Integer.parseInt(cursor.getString(0)));
        student.setPrenom(cursor.getString(1));
        student.setNom(cursor.getString(2));
        student.setMatricule(cursor.getString(3));
        cursor.close();
        return student;
    }


    public int updateStudent(student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRENOM, student.getPrenom());
        values.put(COLUMN_NOM, student.getNom());
        values.put(COLUMN_MATRICULE, student.getMatricule());
        return db.update(TABLE_STUDENT, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    public void deleteStudent(student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, COLUMN_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }


    public List<student> getAllStudents() {
        List<student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                student student = new student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setPrenom(cursor.getString(1));
                student.setNom(cursor.getString(2));
                student.setMatricule(cursor.getString(3));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }
}
