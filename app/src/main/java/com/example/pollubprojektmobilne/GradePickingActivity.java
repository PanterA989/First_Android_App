package com.example.pollubprojektmobilne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GradePickingActivity extends AppCompatActivity {

    private List<GradeModel> gradesList = new ArrayList<>(); //Lista obiektow ocen
    private ListView gradesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_picking); //Ustawienie GUI z layoutu

        Bundle bundle = getIntent().getExtras(); //Nowy Bundle
        int gradesCount = bundle.getInt("gradesCount"); //Pobranie liczby ocen z Bundle
        for (int i = 0; i < gradesCount; i++){
            gradesList.add(new GradeModel("Ocena" + (i+1) + ": ")); //Dodawanie nowych obiektow ocen do listy
        }

        //Przywrocenie danych z zapisanego stanu
        if(savedInstanceState != null){
            int[] savedGrades = savedInstanceState.getIntArray("gradesValues"); //Pobranie tablicy zapisanych ocen
            for(int i = 0; i < savedGrades.length; i++){ //Petla przechodzaca po kazdej ocenie
                gradesList.get(i).setGrade(savedGrades[i]); //Ustawianie ocen w kolejnych obiektach listy
            }
        }

        GradePickingAdapter adapter = new GradePickingAdapter(this, gradesList); //Nowy adapter wyboru ocen
        gradesListView = (ListView)findViewById(R.id.gradesList); //pobranie elementu GUI ListView
        gradesListView.setAdapter(adapter); //Ustawienie adapteru do elementu ListView z GUI
    }

    //Funkcja wykonywana po wcisnieciu przycisku do obliczenia sredniej
    public void calculateGrade(View view) {
        if(GradeModel.allGradesPicked(gradesList)){ //Wykonanie jesli wszystkie oceny zostaly wybrane
            Intent resultIntent = new Intent(); //Utworzenie nowego intentu
            resultIntent.putExtra("GPA", GradeModel.calculateGPA(gradesList)); //Dodanie sredniej do Intentu
            setResult(RESULT_OK, resultIntent); //Ustawienie wyniku Activity i przekazanie Intentu
            finish(); //Zakonczenie Activity
        }
        else { //Jesli nie wybrano wszystkich ocen
            Toast.makeText(GradePickingActivity.this, "Nie wybrano wszystkich ocen!", Toast.LENGTH_SHORT).show(); //Wyswietlenie Toast
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        int[] allGrades = new int[gradesList.size()]; //Nowa tablica wielkosci liczby ocen
        for(int i = 0; i < gradesList.size(); i++){ //Petla przechodzaca po kazdej ocenie
            allGrades[i] = gradesList.get(i).getGrade(); //Dodanie do tablicy kolejnych ocen
        }
        outState.putIntArray("gradesValues", allGrades); //Dodanie do Bundle wybranych ocen
        super.onSaveInstanceState(outState);
    }
}