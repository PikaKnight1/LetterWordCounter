package com.company;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Wpisz ścieżkę pliku, którego statystyki chcesz utworzyć.");

        String path = getPath();
        count(path);

        boolean test = true;
    }

    public static String getPath() {
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        File file = new File(path);

        if (file.exists()) return path;
        else {
            System.out.println("Błędna ścieżka! Podaj poprawną ścieżkę lub wpisz 0 by zakończyć działanie programu.");
            return getPath();
        }
    }

    public static void count(String path) {
        long word = 0, letter = 0, sentence = 0;

        try {
            File file = new File(path);
            Scanner scFile = new Scanner(file);

            while(scFile.hasNext())
            {
                String line = scFile.nextLine();
                boolean prevLetter = false;             //zapamiętuje, czy poprzedni znak był literą

                for(char c: line.toCharArray()){

                    if (Character.isLetter(c)) {
                        letter++;
                        prevLetter = true;
                    }

                    //założenie:    zdanie kończy się znakiem interpunkcyjnym, przed którym jest litera
                    //              by uniknąć sytuacji, gdzie trzykropek, czy zwielokrotnione znaki interpunkcyjne uznane zostaną za nowe zdania
                    if (c == '!' || c == '?' || c == '.' && prevLetter) {
                        sentence++;
                        word++;
                        prevLetter = false;
                    }

                    else if (c == ' ' && prevLetter)
                    {
                        word++;
                        prevLetter = false;
                    }
                }
            }
        }
        catch (InvalidPathException | FileNotFoundException ex) {
            System.err.println("Błąd odczytu pliku");
        }

        writeToFile(letter,word,sentence,path);
}

    private static void writeToFile(long letter, long word, long sentence, String path) {
        try {
            PrintWriter newFile = new PrintWriter(path + ".stat");
            newFile.write("Liczba liter: " + letter + "\r\n");
            newFile.write("Liczba słów: " + word + "\r\n");
            newFile.write("Liczba zdań: " + sentence + "\r\n");
            newFile.close();

            System.out.println("Utworzono plik, dowolny przycisk kończy działanie programu.");
        }
        catch (FileNotFoundException e) {
            System.err.println("Błąd odczytu lub zapisu pliku");
        }
    }
}
