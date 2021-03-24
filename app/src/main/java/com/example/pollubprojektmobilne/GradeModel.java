package com.example.pollubprojektmobilne;

import java.util.List;

public class GradeModel {
    private String name;
    private int grade = 0;

    public GradeModel(String name, int grade){ //Konsturuktor ze wszystkimi polami
        this.name = name;
        this.grade = grade;
    }

    public GradeModel(int grade) {
        this.grade = grade;
    } //Konstruktor z sama ocena (nieuzywane)

    public GradeModel(String name) {
        this.name = name;
    } //Konstruktor z sama nazwa

    public int getGrade(){
        return grade;
    } //Getter oceny

    public String getName() {
        return name;
    } //Getter etykiety oceny

    public void setGrade(int grade) { //Setter oceny
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    } //Setter etykiety (nieuzywane)

    //Metoda sprawdzajaca czy wszystkie elementy w liscie ocen maja wybrane oceny
    public static boolean allGradesPicked(List<GradeModel> allGrades){
        for (GradeModel grade: allGrades) { //Wykonanie dla kazdej oceny z listy
            if(grade.getGrade() == 0) return false; //Sprawdzenie czy kazda ma wartosc inna niz 0
        }
        return true;
    }

    //Metoda obliczajaca srednia ze wszystkich ocen z listy
    public static float calculateGPA(List<GradeModel> allGrades){
        float suma = 0;
        for (GradeModel grade: allGrades) { //Wykonanie dla kazdej oceny z listy
            suma += grade.getGrade(); //Dodawanie do sumy
        }
        return suma/allGrades.size(); //Zwracanie sredniej
    }
}
