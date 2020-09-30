package com.example.ensi;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;          //package où les classes permettent la manipulation des fichiers
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Etudiants_list extends AppCompatActivity {

    ArrayList<Student> Etudiants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String classname = "Students";
        String modulename = "modules";
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            classname = extras.getString("Class");
            modulename = extras.getString("module");
        }

        this.setTitle(classname + " - " + modulename);


            String [] dataetudiants = {"Nom1   Prénom1" , "Nom2   Prénom2" , "Nom3   Prénom3" , "Nom4   Prénom4" , "Nom5   Prénom5" , "Nom6   Prénom6" , "Nom7   Prénom7" , "Nom8   Prénom8" , "Nom9   Prénom9" ,"Nom10  Prénom10"};



        ListView listview = (ListView) findViewById(R.id.listview);

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);  //check more than one checkbox in the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.etudiants_layout, R.id.txt_ch, dataetudiants);
        listview.setAdapter(adapter);
        for (int i = 0; i < dataetudiants.length; i++) {
            Etudiants.add(new Student(dataetudiants[i]));
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {      //mal9it manekteb 7asitou wadhe7 tbh
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = ((TextView) view).getText().toString();
                if (Etudiants.get(position).getStudent_name().equals(selectedItem)) {
                    Etudiants.get(position).setChecked("A");

                }
                else{
                    Etudiants.get(position).setChecked("");
                }
            }
        });

    }

    public void export(View view) { // la création d'un fichier excel a eté fait grace à l'implémentation du biblioteque apache POI

        Workbook wb = new HSSFWorkbook();   //créer l'interface
        Cell cell = null;

        CellStyle cellStyle = wb.createCellStyle();                                         //apply formatting
        CellStyle cellStyle2 = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);





        Sheet sheet = null;     //créer le sheet (tableau)
        String classe = this.getTitle().toString();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("   yyyy-MMM-dd   HH:mm");
        String strDate = dateFormat.format(date);
        sheet = wb.createSheet("ENSI" + " - " + classe);
        sheet.setDefaultRowHeight((short) 350);


        Row row = sheet.createRow(0);     //créer le premier ligne, ce ligne a deux cases (cells)

        cell = row.createCell(1);       // 1 : la date
        cell.setCellValue("  " + strDate);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);     // 2 : abscences (tout en appliquant les formats determinés au dessus pour chaque case)
        cell.setCellValue("    Absences");
        cell.setCellStyle(cellStyle);


        for (int i = 1; i < Etudiants.size() + 1; i++) {

            Row row2 = sheet.createRow(i);
            Cell num = row2.createCell(0);  // colone 1 : num d'etudiant
            Cell list_et = row2.createCell(1); // colone 2 : liste des etudiants
            Cell absence = row2.createCell(2); //colone 3 : l'affectation fait pour chaque etudiant (absent ou non)
            String name = Etudiants.get(i - 1).getStudent_name();
            String ab = Etudiants.get(i-1).getChecked();
            String stri = String.valueOf(i);

            list_et.setCellValue("     " + name);
            num.setCellValue("  Etudiant " + stri);
            absence.setCellValue("   " + ab);
            num.setCellStyle(cellStyle);
            absence.setCellStyle(cellStyle2);
            list_et.setCellStyle(cellStyle2);

        }

        sheet.setColumnWidth(0, (100 * 50));
        sheet.setColumnWidth(1, (100 * 50));
        sheet.setColumnWidth(2, (100 * 30));

        int compt = 0;
        String dirpath = getExternalFilesDir(null).getAbsolutePath();
        File file = new File(dirpath + "/ENSI_" + classe + "_" + compt + ".xls");

        String lastFilePath = null;

        if (file.exists()) {
            lastFilePath = file.getAbsolutePath();
            compt++;
        }

        FileOutputStream outputStream = null;


        try {
            outputStream = new FileOutputStream(file); //c'est la classe (flux) permettant d'ecrir les données
            wb.write(outputStream);     //création du fichier
            Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                wb.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //pour que l'application sera capable d'ecrir ces données au stockage externe, on ajoute une permission au fichier manifest.



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                View view = null;
                export(view);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



