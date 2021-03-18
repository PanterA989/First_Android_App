package com.example.pollubprojektmobilne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GradePickingActivity extends AppCompatActivity {

    private List<GradeModel> gradesList = new ArrayList<GradeModel>();
    private ListView gradesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_picking);

        Bundle bundle = getIntent().getExtras();
        int gradesCount = bundle.getInt("gradesCount");
        for (int i = 0; i < gradesCount; i++){
            gradesList.add(new GradeModel("Ocena" + (i+1) + ": "));
        }

        GradePickingAdapter adapter = new GradePickingAdapter(this, gradesList);
        gradesListView = (ListView)findViewById(R.id.gradesList);
        gradesListView.setAdapter(adapter);
    }

    public void calculateGrade(View view) {
        if(GradeModel.allGradesPicked(gradesList)){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("GPA", GradeModel.calculateGPA(gradesList));
            setResult(RESULT_OK, resultIntent);
            finish();
        }
        else {
            Toast.makeText(GradePickingActivity.this, "Nie wybrano wszystkich ocen!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }
}