package com.example.tp3;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText matriculeEditText, nomEditText, prenomEditText;
    Button ajouterButton, afficherButton;
    DBHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matriculeEditText = findViewById(R.id.matriculeEditText);
        nomEditText = findViewById(R.id.nomEditText);
        prenomEditText = findViewById(R.id.prenomEditText);
        ajouterButton = findViewById(R.id.ajouterButton);
        afficherButton = findViewById(R.id.afficherButton);

        dbHelper = new DBHandler(this);


        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricule = matriculeEditText.getText().toString();
                String nom = nomEditText.getText().toString();
                String prenom = prenomEditText.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("matricule", matricule);
                values.put("nom", nom);
                values.put("prenom", prenom);

                try {
                    long newRowId = db.insert("student", null, values);
                    if (newRowId != -1) {
                        Toast.makeText(MainActivity.this, "Student added successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to add student.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("InsertError", "Error inserting student: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Failed to add student. Check logs for details.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        afficherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AfficherActivity.class);
                startActivity(intent);
            }
        });
    }
}
