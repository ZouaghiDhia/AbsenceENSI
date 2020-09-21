package com.example.ensi;

public class Student {
    private String student_name;
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Student(String student_name) {
        this.student_name = student_name;
        this.checked = "";
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
}
