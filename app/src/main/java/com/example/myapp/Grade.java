package com.example.myapp;

public class Grade {
    String date,subject;
    Long grade,regNr;

    public Grade(){

    }

    public Grade(String date, Long grade, Long regNr, String subject) {
        this.date = date;
        this.grade = grade;
        this.regNr = regNr;
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public Long getRegNr() {
        return regNr;
    }

    public void setRegNr(Long regNr) {
        this.regNr = regNr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
