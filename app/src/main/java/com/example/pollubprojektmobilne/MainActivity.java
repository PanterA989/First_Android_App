package com.example.pollubprojektmobilne;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameInput, lastNameInput, gradesCountInput;
    private TextView warningTextView;
    private String[] errors = new String[3];
    private Button buttonOceny;
    private float gpa = 0;
    private String gpaMessage;

    private static final int REQUEST_FOR_GPA = 836;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        gradesCountInput = findViewById(R.id.gradesCountInput);
        warningTextView = findViewById(R.id.warningsTextView);
        buttonOceny = findViewById(R.id.buttonOceny);

        firstNameInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!Validator.checkName(s.toString())) {
                            errors[0] = getString(R.string.firstNameWarningText);
                        } else {
                            errors[0] = null;
                        }
                        updateErrors();
                    }
                }
        );

        lastNameInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!Validator.checkName(s.toString())) {
                            errors[1] = getString(R.string.lastNameWarningText);
                        } else {
                            errors[1] = null;
                        }
                        updateErrors();
                    }
                }
        );

        gradesCountInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!Validator.checkGradesCount(s.toString())) {
                            errors[2] = getString(R.string.gradesCountWarningText);
                        } else {
                            errors[2] = null;
                        }
                        updateErrors();
                    }
                }
        );
    }

    private void updateErrors() {
        warningTextView.setText("");
        boolean allGood = true;
        for (String error : errors) {
            if (error != null) {
                warningTextView.append(error + "\n");
                allGood = false;
                buttonOceny.setVisibility(Button.INVISIBLE);
            }
        }
        if(firstNameInput.length() == 0 || lastNameInput.length() == 0 || gradesCountInput.length() == 0){
            buttonOceny.setVisibility(Button.INVISIBLE);
            return;
        }

        if (allGood && firstNameInput.length() != 0 && lastNameInput.length() != 0 && gradesCountInput.length() != 0) {
            buttonOceny.setVisibility(Button.VISIBLE);
            if(firstNameInput.getText().toString().equals("Barrel") &&  lastNameInput.getText().toString().equals("Roll")){
                RotateAnimation rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(1000);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                buttonOceny.startAnimation(rotateAnimation);
            }
        }

    }


    public void submitGrades(View view) {
        if(gpa > 0){
            Toast.makeText(MainActivity.this, gpaMessage, Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            } finally {
                this.finishAndRemoveTask();
            }

        } else {
            Intent intent = new Intent(this, GradePickingActivity.class);
            intent.putExtra("gradesCount", Integer.parseInt(gradesCountInput.getText().toString()));
            startActivityForResult(intent, REQUEST_FOR_GPA);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_FOR_GPA){
            if(resultCode == RESULT_OK){
                gpa = data.getFloatExtra("GPA", 0);
                firstNameInput.setEnabled(false);
                lastNameInput.setEnabled(false);
                gradesCountInput.setEnabled(false);
                if(gpa >= 3){
                    String text = "Super :)";
                    buttonOceny.setText(text);
                    gpaMessage = "Gratulacje! Otrzymujesz zaliczenie!";
                } else {
                    String text = "Tym razem mi nie poszło.";
                    buttonOceny.setText(text);
                    gpaMessage = "Wysyłam podanie o zaliczenie warunkowe.";
                }
                String gpaValueMessage = "Twoja średnia to: "+String.format("%.02f",gpa);
                warningTextView.setText(gpaValueMessage);
            }
        }
    }
}