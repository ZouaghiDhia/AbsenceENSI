package com.example.ensi;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class modules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String classname = "classe";
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            classname = extras.getString("Class");
        }

        this.setTitle(classname);

        final EditText editText = findViewById(R.id.editmodule);
        final Button button = findViewById(R.id.buttonmodule);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button.setText(editText.getText().toString());
                editText.getText().clear();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().toString().trim().length() <= 0) {
                    Toast.makeText(modules.this, "Please enter the course name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(modules.this, Etudiants_list.class);
                    String classname = getTitle().toString();
                    String modulename = button.getText().toString();
                    intent.putExtra("Class", classname);
                    intent.putExtra("module", modulename);
                    startActivity(intent);
                }

            }

        });

        
    }

}