package com.example.tp3;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
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

        LinearLayout parentLayout = findViewById(R.id.parentLayout);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String matricule = cursor.getString(cursor.getColumnIndex("matricule"));
                @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex("nom"));
                @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex("prenom"));


                TextView textView = new TextView(this);
                textView.setText("Matricule: " + matricule + "\nNom: " + nom + "\nPrenom: " + prenom);
                textView.setTextSize(20);
                parentLayout.addView(textView);
                Space space = new Space(this);
                space.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20)); // Adjust the height as needed
                parentLayout.addView(space);

            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
