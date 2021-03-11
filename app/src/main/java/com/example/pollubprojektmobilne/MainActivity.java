package com.example.pollubprojektmobilne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameInput, lastNameInput, gradesCountInput;
    private TextView warningTextView;
    private String[] errors = new String[3];
    private Button buttonOceny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        gradesCountInput = findViewById(R.id.gradesCountInput);
        warningTextView = findViewById(R.id.warningsTextView);
        buttonOceny = findViewById(R.id.buttonOceny);

        firstNameInput.setOnFocusChangeListener(
                new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            if (!Validator.checkName(firstNameInput.getText().toString())) {
                                errors[0] = getString(R.string.firstNameWarningText);
                            } else {
                                errors[0] = null;
                            }
                            updateErrors();
                        }
                    }
                }
        );

        lastNameInput.setOnFocusChangeListener(
                new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            if (!Validator.checkName(lastNameInput.getText().toString())) {
                                errors[1] = getString(R.string.lastNameWarningText);
                            } else {
                                errors[1] = null;
                            }
                            updateErrors();
                        }
                    }
                }
        );

        gradesCountInput.setOnFocusChangeListener(
                new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            if (!Validator.checkGradesCount(gradesCountInput.getText().toString())) {
                                errors[2] = getString(R.string.gradesCountWarningText);
                            } else {
                                errors[2] = null;
                            }
                            updateErrors();
                        }
                    }
                }
        );
    }

    private void updateErrors(){
        warningTextView.setText(" ");
        boolean allGood = true;
        for (String error : errors) {
            if (error != null){
                warningTextView.append(error + "\n");
                allGood = false;
            }
        }
        if(allGood){
            buttonOceny.setVisibility(Button.VISIBLE);
        }
    }
}