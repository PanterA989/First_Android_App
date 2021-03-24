package com.example.pollubprojektmobilne;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.util.List;

public class GradePickingAdapter extends ArrayAdapter<GradeModel> {

    private List<GradeModel> gradesList; //Lista obiektow ocen
    private Activity context;

    public GradePickingAdapter(Activity context, List<GradeModel> gradesList) {
        super(context, R.layout.grade_picker_layout, gradesList);
        this.gradesList = gradesList;
        this.context = context;
    }

    @Override
    public View getView(int row, View view, ViewGroup parent){
        GradeModel grade = gradesList.get(row); //Pobranie lementu z listy na podstawie wiersza

        LayoutInflater inflater = context.getLayoutInflater(); //Pobranie Inflater'a
        view = inflater.inflate(R.layout.grade_picker_layout,parent, false); //"Napompowanie" elementu XML reprezentujacego wybieranie pojedynczej oceny
        TextView label = view.findViewById(R.id.gradeName); //Pobranie elemntu GUI dla etykiety
        label.setText(grade.getName()); //Ustawienie nazwy oceny z etykiety
        RadioGroup radioGroup = view.findViewById(R.id.gradesGroup); //Pobranie grupy checkbox'ow z GUI

        int currentGrade = grade.getGrade(); //Pobranie wartosci oceny z obiektu
        if(currentGrade > 0){ //Jesli byla wybrana jakas ocena (0 - wartosc domyslna dla niewybranej oceny)
            //Zaznaczenie ocen w zaleznosci od oceny z obiektu (wykorzystywane przy zmienie konfiguracji)
            if(currentGrade == 2) radioGroup.check(R.id.grade2);
            else if(currentGrade == 3) radioGroup.check(R.id.grade3);
            else if(currentGrade == 4) radioGroup.check(R.id.grade4);
            else if(currentGrade == 5) radioGroup.check(R.id.grade5);
            else radioGroup.clearCheck(); //Ostatecznie odznaczenie wszystkich opcji
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> { //Dodanie nowego listenera dla grupy od zaznaczenia
            if(checkedId == R.id.grade2) grade.setGrade(2);
            else if(checkedId == R.id.grade3) grade.setGrade(3);
            else if(checkedId == R.id.grade4) grade.setGrade(4);
            else if(checkedId == R.id.grade5) grade.setGrade(5);
            else grade.setGrade(0); //Jesli nie jest wybrana ocena ustawiamy wartos na 0
        });
        return view;
    }
}
