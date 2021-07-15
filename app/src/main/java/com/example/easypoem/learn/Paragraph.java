package com.example.easypoem.learn;

import java.io.Serializable;
import java.util.Random;

public class Paragraph implements Serializable {
    public String text;
    public String[] words;
    public String selectedWord;
    public String[] lines;
    public String selectedLine;


    public Paragraph(String text) {
        this.text = text;
        words = text.split("\\b");
        lines = text.split("\\n+");
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
}
