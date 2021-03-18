package com.example.pollubprojektmobilne;

import android.app.Activity;

public class Validator {

    //ToDo: Possible validation for apostrophes and dashes
    public static boolean checkName(String  name){
        name = name.trim();                                             //usunięcie spacji z początku i końca
        if (name.isEmpty() || name == null) return false;               //czy ciąg jest pusty
        else if (!hasValidCharacters(name)) return false;             //czy składa się z czegoś innego niż litery i spacje
        else if (Character.isLowerCase(name.charAt(0)) || !Character.isLetter(name.charAt(0))) return false;   //czy rozpoczyna się od małego znaku lub myślinika bądź apostrofu
        else return true;
    }

    public static boolean checkGradesCount(String grades){
        if(grades.length() < 1 || grades.charAt(0) == '0') return false;
        try {                                                           //z powodu właściwości android:digits="1234567890" nie powinno
            int count = Integer.parseInt(grades);                       //być możliwości wpisywania innych znaków niż zdefiniowane cyfry
            if(count < 5 || count > 15) return false;                   //lecz w razie czego sprawdzamy czy jest to liczba
        } catch (Exception e){
            return false;
        }
        return true;
    }

    private static boolean hasValidCharacters(String name){
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != '-' && c != '\''){ //Sprawdzenie dla Bożena-Anna oraz De'Angelo
                return false;
            }
        }
        return true;
    }
}
