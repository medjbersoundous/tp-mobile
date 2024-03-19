package com.example.tp3;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
    Button ajouterButton, afficherButton, chercherButton, modifierButton, supprimerButton;
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
        chercherButton = findViewById(R.id.chercherButton);
        modifierButton = findViewById(R.id.modifierButton);
        supprimerButton = findViewById(R.id.supprimerButton);

        dbHelper = new DBHandler(this);


        afficherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AfficherActivity.class);
                startActivity(intent);
                matriculeEditText.setText("");
                nomEditText.setText("");
                prenomEditText.setText("");
            }
        });

        chercherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricule = matriculeEditText.getText().toString().trim();
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String[] projection = {"nom", "prenom"};
                String selection = "matricule = ?";
                String[] selectionArgs = {matricule};
                Cursor cursor = db.query("student", projection, selection, selectionArgs, null, null, null);
                if (cursor.moveToFirst()) {
                    String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
                    String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
                    nomEditText.setText(nom);
                    prenomEditText.setText(prenom);
                } else {
                    Toast.makeText(MainActivity.this, "No student found with the provided matricule.", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });
        modifierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String matricule = matriculeEditText.getText().toString().trim();
                String nom = nomEditText.getText().toString().trim();
                String prenom = prenomEditText.getText().toString().trim();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("nom", nom);
                values.put("prenom", prenom);

                String selection = "matricule = ?";
                String[] selectionArgs = {matricule};

                int rowsAffected = db.update("student", values, selection, selectionArgs);
                if (rowsAffected > 0) {
                    Toast.makeText(MainActivity.this, "Student information updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No student found with the provided matricule.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricule = matriculeEditText.getText().toString().trim();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String selection = "matricule = ?";
                String[] selectionArgs = {matricule};

                int rowsDeleted = db.delete("student", selection, selectionArgs);
                if (rowsDeleted > 0) {
                    Toast.makeText(MainActivity.this, "Student information deleted successfully!", Toast.LENGTH_SHORT).show();
                    matriculeEditText.setText("");
                    nomEditText.setText("");
                    prenomEditText.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "No student found with the provided matricule.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
