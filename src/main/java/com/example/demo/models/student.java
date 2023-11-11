package com.example.demo.models;

public class student {
    private String fname;
    private String lname;
    private String gender;
    private String gpa;

    public void setfname(String f) {
        this.fname = f;
    }

    public void setlname(String l) {
        this.lname = l;
    }

    public void setgender(String g) {
        this.gender = g;
    }

    public void setgpa(String g) {
        this.gpa = g;
    }

    @Override
    public String toString() {
        return "" + fname + "" + lname + "" + gender + "" + gpa;
    }
}
