package com.example.pollubprojektmobilne;

public class Validator {

    //Metoda sprawdzajaca dla imienia oraz nazwiska
    public static boolean checkName(String  name){
        name = name.trim(); //Usuniecie spacji z poczatku i konca tekstu
        if (name.isEmpty() || name == null) return false; //Sprawdzenie czy ciąg jest pusty
        else if (!hasValidCharacters(name)) return false; //Sprawdzenie czy sklada się z czegoś innego niż litery i spacje
        else return !Character.isLowerCase(name.charAt(0)) && Character.isLetter(name.charAt(0)); //Sprawdzenie czy rozpoczyna sie od malego znaku, myslinika lub apostrofu
    }

    //Metoda sprawdzajaca dla liczby ocen
    public static boolean checkGradesCount(String grades){
        if(grades.length() < 1 || grades.charAt(0) == '0') return false; //Jesli nie wpisano liczby badz zaczyna sie od zera
        try { //Z powodu wlasciwosci android:digits="1234567890" nie powinno byc mozliwosci wpisywania innych znakow niż zdefiniowane cyfry, ale w razie czego sprawdzamy czy jest to liczba
            int count = Integer.parseInt(grades); //Proba przekonwertowania String w int
            if(count < 5 || count > 15) return false; //Sprawdzenie czy pasuje do przedzialu
        } catch (Exception e){
            return false;
        }
        return true;
    }

    //Metoda sprawdzajaca poprawnosc tekstu dla imienia i nazwiska (dozwolone litery, spacje, myslniki i apostrofy)
    private static boolean hasValidCharacters(String name){
        for (char c : name.toCharArray()) { //Wykonanie dla kazdego znaku ze String
            if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != '-' && c != '\''){ //Sprawdzenie dla Bozena-Anna oraz De'Angelo
                return false;
            }
        }
        return true;
    }
}
