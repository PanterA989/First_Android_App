package com.example.pollubprojektmobilne;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GradePickingAdapter extends ArrayAdapter<GradeModel> {

    private List<GradeModel> gradesList;
    private Activity context;

    public GradePickingAdapter(Activity context, List<GradeModel> gradesList) {
        super(context, R.layout.grade_picker_layout, gradesList);
        this.gradesList = gradesList;
        this.context = context;
    }

    @Override
    public View getView(int row, View view, ViewGroup parent){
        GradeModel grade = gradesList.get(row);

        if(view == null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.grade_picker_layout,parent, false); //What happens if true?
        }
        TextView label = view.findViewById(R.id.gradeName);
        label.setText(grade.getName());
        RadioGroup radioGroup = view.findViewById(R.id.gradesGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.grade2) grade.setGrade(2);
            else if(checkedId == R.id.grade3) grade.setGrade(3);
            else if(checkedId == R.id.grade4) grade.setGrade(4);
            else if(checkedId == R.id.grade5) grade.setGrade(5);
            else grade.setGrade(0);
        });
        return view;
    }


}
