package com.example.tp3;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AfficherActivity extends AppCompatActivity {

    DBHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher);

        dbHelper = new DBHandler(this);
        afficherTous();
    }

    private void afficherTous() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM student", null);

        TextView textView = findViewById(R.id.textView);

        if (cursor.moveToFirst()) {
            StringBuilder stringBuilder = new StringBuilder();
            do {
                @SuppressLint("Range") String matricule = cursor.getString(cursor.getColumnIndex("matricule"));
                @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex("nom"));
                @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex("prenom"));
                stringBuilder.append("Matricule: ").append(matricule).append(", Nom: ").append(nom).append(", Prenom: ").append(prenom).append("\n");
            } while (cursor.moveToNext());

            textView.setText(stringBuilder.toString());
        } else {
            Toast.makeText(this, "No students found.", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
