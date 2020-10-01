package com.example.ensi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;


public class newActivity1 extends AppCompatActivity {

    public ArrayList<Class> Classes;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private MyAdapter.mClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Classes");
        ImageView Remove = findViewById(R.id.OptionsMenu);


        loadData();
        creerRecyclerView();
        setbouton();

    }


    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Classes);
        editor.putString("key", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("key", null);
        Type type = new TypeToken<ArrayList<Class>>() {}.getType();
        Classes = gson.fromJson(json, type);
        if (Classes == null) {
            Classes = new ArrayList<>();
        }
    }

    private void removeValue() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("key");
        editor.apply();
    }




    private void creerRecyclerView() {
        setOnClickListener(); // on passe à l'activité modules en cliquanr sur le cardview
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview); //avoir acces au recyclerview via id
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this); // c'est un layout manager prédefinie qui determine comment les cardviews seront affichée, ici c'est lineairement (thus the name DUH)
        mAdapter = new MyAdapter(Classes, listener);
        mRecyclerView.setLayoutManager(mLayoutManager); //attacher le layoutmanager au recyclerview
        mRecyclerView.setAdapter(mAdapter); //attacher l'adapter au recyclerview


    }

    private void setOnClickListener() { //passer à l'activité module lorsque le view est cliqué (listener definie en adapter does the work and now we JUST tell them what to do)

        listener = new MyAdapter.mClickListener() {
            @Override
            public void monClick(View v, int position) {

                Intent intent = new Intent(getApplicationContext(), modules.class);
                String class_name = Classes.get(position).getClasse_name();
                intent.putExtra("Class", class_name);  //sauvegarder le nom du classe du cardview choisi
                startActivity(intent);


            }
        };
    }

    private void setbouton() {
        final Button buttonInsert = findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText line = findViewById(R.id.edittext);


                if (line.getText().toString().trim().length() <= 0) {
                    Toast.makeText(newActivity1.this, "Please enter the class name", Toast.LENGTH_SHORT).show();
                }

                else {


                    String[] classes_ensi = {"II1A", "II1B", "II1C", "II1D", "II1E", "II1F", "II2A", "II2B", "II2C", "II2D", "II2E", "II2F"};
                    Toast toast1 = null;

                    boolean Contains = Arrays.asList(classes_ensi).contains(line.getText().toString());
                    if (!(Contains)){
                        toast1 = Toast.makeText(newActivity1.this, "this class does not exist in ENSI", Toast.LENGTH_SHORT);
                        toast1.show();
                    }


                        else  {

                                insertitem(line.getText().toString());
                                saveData();
                                line.getText().clear();

                            }


                        }



                    }








        });

    }

    private void insertitem(String line1) {
        Classes.add(new Class(line1));
        mAdapter.notifyItemInserted(Classes.size());
    }





}













