package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText editId, editName;
    Button btnAdd, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS students(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(view -> {
            db.execSQL("INSERT INTO students(name) VALUES(?)", new Object[]{editName.getText().toString()});
            Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
        });

        btnView.setOnClickListener(view -> {
            Cursor c = db.rawQuery("SELECT * FROM students", null);
            if (c.getCount() == 0) {
                Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder data = new StringBuilder();
            while (c.moveToNext()) {
                data.append("ID: ").append(c.getInt(0)).append(", Name: ").append(c.getString(1)).append("\n");
            }
            Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
        });

        btnUpdate.setOnClickListener(view -> {
            db.execSQL("UPDATE students SET name=? WHERE id=?", new Object[]{editName.getText().toString(), editId.getText().toString()});
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(view -> {
            db.execSQL("DELETE FROM students WHERE id=?", new Object[]{editId.getText().toString()});
            Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
        });
    }
}
