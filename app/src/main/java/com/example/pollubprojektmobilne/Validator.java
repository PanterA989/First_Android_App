package com.example.pollubprojektmobilne;

import android.app.Activity;

public class Validator {

    public static boolean checkName(String  name){
        name = name.trim();                                             //usunięcie spacji z początku i końca
        if (name.isEmpty() || name == null) return false;               //czy ciąg jest pusty
//        else if(haveDigits(name)) return false;
        else if (!isLetterOrWhitespace(name)) return false;             //czy składa się z czegoś innego niż litery i spacje
        else if (Character.isLowerCase(name.charAt(0))) return false;   //czy rozpoczyna się od małego znaku
        else return true;
    }

    public static boolean checkGradesCount(String grades){
        try {                                                           //z powodu właściwości android:digits="1234567890" nie powinno
            int count = Integer.parseInt(grades);                       //być możliwości wpisywania innych znaków niż zdefiniowane cyfry
            if(count < 5 || count > 15) return false;                   //lecz w razie czego sprawdzamy czy jest to liczba
        } catch (Exception e){
            return false;
        }
        return true;
    }

//    private static boolean haveDigits(String name){
//        for (char c : name.toCharArray()) {
//            if (Character.isDigit(c)){
//                return true;
//            }
//        }
//        return false;
//    }

    private static boolean isLetterOrWhitespace(String name){
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)){
                return false;
            }
        }
        return true;
    }


}
