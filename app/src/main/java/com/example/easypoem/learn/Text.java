package com.example.easypoem.learn;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Text {
    public String text;
    public String[] words;
    public String selectedWord;
    public String[] lines;
    public String selectedLine;
    public Text[] paragraph;
    public Text selectedParagraph;


    public Text(String text) {
        this.text = text;
        words = text.split("\\b");
        lines = text.split("\\n+");
        Log.println(Log.DEBUG, "DEBUG", String.valueOf(getLengthParagraphs()));
        if (getLengthParagraphs() == 0) {
            paragraph[0] = new Text(text);
        } else {
            String[] tempParagraph = new String[getLengthParagraphs()];
            paragraph = new Text[getLengthParagraphs()];
            tempParagraph[0] = "";
            int j = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].equals(" ")) {
                    paragraph[j] = new Text(tempParagraph[j]);
                    j++;
                }
                tempParagraph[j] += lines[i] + "\n";
            }
        }
    }
    public int getLengthParagraphs() {
        int j = 1;
        for (int i = 0; i < lines.length ; i++) {
            if (lines[i].equals(" "))
                j++;
        }
        return j;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void getText(String text) {
        this.text = text;
    }

    public int randomWord() {
        Random random = new Random();
        int r = random.nextInt(words.length);

        boolean var = true;

        while (var) {
            if ((words[r].matches("([\n ,.:;?!«»…—-])*")) || (words[r].equals(selectedWord))) {
                r = random.nextInt(words.length);
//                System.out.println(words[r]);
            } else {
                var = false;
            }
        }

//        selectedWord = words[r];
        return r;
    }

    public int randomLine() {
        Random random = new Random();
        int r = random.nextInt(lines.length);

        boolean var = true;

        while (var) {
            if (lines[r].equals(selectedLine)) {
                r = random.nextInt(lines.length);
//                System.out.println(words[r]);
            } else {
                var = false;
            }
        }

//        selectedLine = lines[r];
        return r;
    }

    public void setWord(int i, String word) {
        words[i] = word;
    }

    public String getWord(int i) {
        return words[i];
    }

    public void selectWord(int i) {
        selectedWord = words[i];
    }

    public void setLine(int i, String line) {
        lines[i] = line;
    }

    public String getLine(int i) {
        return lines[i];
    }

    public void selectLine(int i) {
        selectedLine = lines[i];
    }

    public void setParagraph(int i, Text p) {
        paragraph[i] = p;
    }

    public Text getParagraph(int i) {
        return paragraph[i];
    }

    public void selectParagraph(int i) {
        selectedParagraph = paragraph[i];
    }
}
