package com.example.pollubprojektmobilne;

import java.util.List;

public class GradeModel {
    private String name;
    private int grade = 0;

    public GradeModel(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public GradeModel(int grade) {
        this.grade = grade;
    }

    public GradeModel(String name) {
        this.name = name;
    }

    public int getGrade(){
        return grade;
    }

    public String getName() {
        return name;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean allGradesPicked(List<GradeModel> allGrades){
        for (GradeModel grade: allGrades) {
            if(grade.getGrade() == 0) return false;
        }
        return true;
    }

    public static float calculateGPA(List<GradeModel> allGrades){
        float suma = 0;
        for (GradeModel grade: allGrades) {
            suma += grade.getGrade();
        }
        return suma/allGrades.size();
    }
}
