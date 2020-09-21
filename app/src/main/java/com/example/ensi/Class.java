package com.example.ensi;

import java.util.ArrayList;

public class Class {
    private String classe_name;
    private String module_name;
    private ArrayList<Student> students;


    public String getClasse_name() {
        return classe_name;
    }

    public String getModule_name() {
        return module_name;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setClasse_name(String classe_name) {
        this.classe_name = classe_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public Class(String classe_name, String module_name, ArrayList<Student> students) {
        this.classe_name = classe_name;
        this.module_name = module_name;
        this.students=students;
    }



    public Class (String Classe_name) {
        this.module_name = "module";
        this.classe_name = classe_name;
        this.students = new ArrayList<>();

    }




}
