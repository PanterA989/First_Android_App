package com.example.pollubprojektmobilne;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private EditText firstNameInput, lastNameInput, gradesCountInput;
    private TextView warningTextView;
    private String[] errors = new String[3]; //Tablica bledow
    private Button buttonOceny;
    private float gpa = 0; //Srednia
    private String gpaMessage, gpaButtonText, gpaValueMessage;
    private boolean haveGPA = false; //Flaga obliczonej sredniej

    private static final int REQUEST_FOR_GPA = 836; //Losowy niezajety request code dla obliczenia GPA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Ustawienie GUI z layoutu

        //Przypisanie elementow GUI do zmiennych
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        gradesCountInput = findViewById(R.id.gradesCountInput);
        warningTextView = findViewById(R.id.warningsTextView);
        buttonOceny = findViewById(R.id.buttonOceny);

        //Dodanie TextWatcherow
        firstNameInput.addTextChangedListener(firstNameTextWatcher);
        lastNameInput.addTextChangedListener(lastNameTextWatcher);
        gradesCountInput.addTextChangedListener(gradesCountTextWatcher);

        //Przywrocenie danych z zapisanego stanu
        if(savedInstanceState != null){
            firstNameInput.setText(savedInstanceState.getString("firstName")); //Wprowadzenie tekstu do EditText imienia z zapisanej wartosci
            lastNameInput.setText(savedInstanceState.getString("lastName")); //Wprowadzenie tekstu do EditText naziwska z zapisanej wartosci
            gradesCountInput.setText(savedInstanceState.getString("gradesCount")); //Wprowadzenie tekstu do EditText liczby ocen z zapisanej wartosci
            warningTextView.setText(savedInstanceState.getString("warnings")); //Wprowadzenie tekstu do TextView wiadomosci pod przyciskiem z zapisanej wartosci

            haveGPA = savedInstanceState.getBoolean("haveGPA"); //Pobranie flagi obliczonej sredniej
            if(haveGPA){
                gpa = savedInstanceState.getFloat("calculatedGPA"); //Pobranie sredniej
                gpaButtonText = savedInstanceState.getString("gpaButtonText"); //Pobranie tekstu dla przycisku
                gpaMessage = savedInstanceState.getString("gpaMessage"); //Pobranie tekstu dla TextView dla Toast
                gpaValueMessage = savedInstanceState.getString("gpaValueMessage");  //Pobranie tekstu dla TextView pod przyciskiem

                firstNameInput.setEnabled(false); //Zablokowanie wpisywania do TextEditora imienia
                lastNameInput.setEnabled(false); //Zablokowanie wpisywania do TextEditora nazwiska
                gradesCountInput.setEnabled(false); //Zablokowanie wpisywania do TextEditora liczby ocen
                buttonOceny.setText(gpaButtonText); //Ustawienie tekstu przycisku
                buttonOceny.setVisibility(Button.VISIBLE); //Ustawienie widocznosci przycisku
                warningTextView.setText(gpaValueMessage); //Ustawienie tekstu pod przyciskiem
            }
        }
    }


    //Utworzenie TextWatchera imienia
    private TextWatcher firstNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(gpa == 0){
                if (!Validator.checkName(s.toString()) && s.toString().length() != 0) { //Sprawdzenie poprawnosci imienia
                    errors[0] = getString(R.string.firstNameWarningText); //Ustawienie tekstu bledu do tablicy
                } else {
                    errors[0] = null; //Usuniecie tekstu bledu z tablicy
                }
                updateErrors(); //Wywolanie metody oblugujacej bledy
            }
        }
    };

    //Utworzenie TextWatchera nazwiska
    private TextWatcher lastNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(gpa == 0){
                if (!Validator.checkName(s.toString()) && s.toString().length() != 0) { //Sprawdzenie poprawnosci nazwiska
                    errors[1] = getString(R.string.lastNameWarningText); //Ustawienie tekstu bledu do tablicy
                } else {
                    errors[1] = null; //Usuniecie tekstu bledu z tablicy
                }
                updateErrors(); //Wywolanie metody oblugujacej bledy
            }
        }
    };

    //Utworzenie TextWatchera liczby ocen
    private TextWatcher gradesCountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(gpa == 0){
                if (!Validator.checkGradesCount(s.toString()) && s.toString().length() != 0) { //Sprawdzenie poprawnosci liczby ocen
                    errors[2] = getString(R.string.gradesCountWarningText); //Ustawienie tekstu bledu do tablicy
                } else {
                    errors[2] = null; //Usuniecie tekstu bledu z tablicy
                }
                updateErrors(); //Wywolanie metody oblugujacej bledy
            }
        }
    };


    private void updateErrors() {
        warningTextView.setText(""); //Wyczyszczenie TextView z bledami
        boolean allGood = true;
        for (String error : errors) { //Petla po wszystkich elemantach tablicy z bledami
            if (error != null) {
                warningTextView.append(error + "\n"); //Dodanie tekstu bledu pod przyciskiem
                allGood = false;
                buttonOceny.setVisibility(Button.INVISIBLE); //Zmiana widocznosci przycisku
            }
        }
        if(firstNameInput.length() == 0 || lastNameInput.length() == 0 || gradesCountInput.length() == 0){ //Sprawdzenie czy pola nie sa puste
            buttonOceny.setVisibility(Button.INVISIBLE); //Zmiana widocznosci przycisku
            return;
        }

        if (allGood && firstNameInput.length() != 0 && lastNameInput.length() != 0 && gradesCountInput.length() != 0) { //Wykonanie jesli wprowadzone dane sa poprawne
            buttonOceny.setVisibility(Button.VISIBLE);
            if(firstNameInput.getText().toString().equals("Barrel") &&  lastNameInput.getText().toString().equals("Roll")){ //Specjalna niespodzianka
                RotateAnimation rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(1000);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                buttonOceny.startAnimation(rotateAnimation);
            }
        }
    }


    public void submitGrades(View view) {
        if(gpa > 0){ //Wykonanie jesli zostala obliczona srednia
            Toast myTosas =Toast.makeText(this, gpaMessage, Toast.LENGTH_SHORT); //Utworzenie Toast
            myTosas.show(); //Wyswietlenie Toast na dole ekranu
            try {
                Thread.sleep(2000); //Uspienie na 2 sekundy
            } catch (InterruptedException e) {
            } finally {
                this.finishAndRemoveTask(); //Zamkniecie aplikacji i usuniecie jej z ostatnich aplikacji
            }

        } else {
            Intent intent = new Intent(this, GradePickingActivity.class); //Nowy Intent z deklaracja nowej aktywnosci
            intent.putExtra("gradesCount", Integer.parseInt(gradesCountInput.getText().toString())); //Dodanie liczby ocen do Intentu
            startActivityForResult(intent, REQUEST_FOR_GPA); //Urychomienie nowej aktywnosci z ustalonym request code
        }
    }

    //Wykonanie po zakonczeniu wywolanego Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_FOR_GPA){ //Jesli wykonany byl Intent do obliczenia oceny
            if(resultCode == RESULT_OK){ //Jesli zwrocony kod to OK
                haveGPA = true; //ustawienie flagi o obliczonej sredniej
                gpa = data.getFloatExtra("GPA", 0); //pobranie wartosci z Intentu
                firstNameInput.setEnabled(false); //Zablokowanie EditText imienia
                lastNameInput.setEnabled(false);  //Zablokowanie EditText nazwiska
                gradesCountInput.setEnabled(false); //Zablokowanie EditText liczby ocen
                if(gpa >= 3){ //Jesli ocena wieksza badz rowna 3
                    gpaButtonText = "Super :)"; //Przypisanie tekstu dla przycisku do zmiennej
                    buttonOceny.setText(gpaButtonText); //Ustawienie tekstu przycisku ze zmiennej
                    gpaMessage = "Gratulacje! Otrzymujesz zaliczenie!"; //Ustawienie zawartosci zmiennej z tekstem dla powiadomienia Toast
                } else {
                    gpaButtonText = "Tym razem mi nie poszło."; //Przypisanie tekstu dla przycisku do zmiennej
                    buttonOceny.setText(gpaButtonText); //Ustawienie tekstu przycisku ze zmiennej
                    gpaMessage = "Wysyłam podanie o zaliczenie warunkowe."; //Ustawienie zawartosci zmiennej z tekstem dla powiadomienia Toast
                }
                gpaValueMessage = "Twoja średnia to: "+String.format("%.02f",gpa);  //Ustawienie zawartosci zmiennej z tekstem dla TextView pod przyciskiem
                warningTextView.setText(gpaValueMessage); //Ustawienie tekstu z obliczona srednia do TextView pod przyciskiem
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("firstName", firstNameInput.getText().toString()); //Zapisanie tekstu imienia
        outState.putString("lastName", lastNameInput.getText().toString()); //Zapisanie tekstu nazwiska
        outState.putString("gradesCount", gradesCountInput.getText().toString()); //Zapisanie tekstu liczby ocen
        outState.putString("warnings", warningTextView.getText().toString());  //Zapisanie tekstu z TextView pod przyciskiem
        outState.putBoolean("haveGPA", haveGPA);  //Zapisanie flagi obliczenia sredniej

        if(haveGPA){
            outState.putFloat("calculatedGPA", gpa); //Zapisanie sredniej
            outState.putString("gpaButtonText", gpaButtonText); //Zapisanie tekstu dla przycisku
            outState.putString("gpaMessage", gpaMessage); //Zapisanie tekstu dla TextView dla Toast
            outState.putString("gpaValueMessage", gpaValueMessage); //Zapisanie tekstu dla TextView pod przyciskiem
        }
        super.onSaveInstanceState(outState);
    }
}