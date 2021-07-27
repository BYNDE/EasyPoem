package com.example.easypoem.learn;

import android.content.Intent;
import android.util.Log;

import com.example.easypoem.check_learn_progress;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Text implements Serializable {
    public String text;
    public String[] words;
    public String selectedWord;
    public String[] lines;
    public String selectedLine;
    public Paragraph[] paragraph;
    public Paragraph selectedParagraph;
    public int countLevels;
    public int level;


    public Text(String text) {
        this.text = text;
        words = text.split("\\b");
        lines = text.split("\\n+");
        if (getLengthParagraphs() != 1 && linearSearch(lines, " ") == -1) {
            String[] tempParagraph = new String[getLengthParagraphs()];
            paragraph = new Paragraph[getLengthParagraphs()];
            tempParagraph[0] = "";

            double src = lines.length / 5;
            int res = (int)src; //целая часть
            double answer = (src - res) * 10;
            if (answer >= 5) {
                answer -= 5;
            }

            int i = 0;
            int j = 0;
            while (i < getLengthParagraphs() - 1) {
                tempParagraph[i] += lines[j++] + "\n";
                if (i+1 == getLengthParagraphs() - 1) {
                    if (j == 4 + answer) {
                        j = 5;
                        paragraph[i] = new Paragraph(tempParagraph[i++]);
                        tempParagraph[i] = "";
                    }
                } else {
                    if (j == 5) {
                        j = 0;
                        paragraph[i] = new Paragraph(tempParagraph[i++]);
                        tempParagraph[i] = "";
                    }
                }
            }
            countLevels = getLengthParagraphs();
        } else if (getLengthParagraphs() == 1) {
            paragraph[0] = new Paragraph(text);
            countLevels = 1;
        } else {
            String[] tempParagraph = new String[getLengthParagraphs()];
            paragraph = new Paragraph[getLengthParagraphs()];
            tempParagraph[0] = "";
            int j = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].equals(" ")) {
                    paragraph[j] = new Paragraph(tempParagraph[j]);
                    j++;
                    tempParagraph[j] = "";
                }
                tempParagraph[j] += lines[i] + "\n";
            }
            countLevels = getLengthParagraphs();
        }
    }

    public int linearSearch(String arr[], String elementToSearch) {

        for (int index = 0; index < arr.length; index++) {
            if (arr[index] == elementToSearch)
                return index;
        }
        return -1;
    }

    public int getLengthParagraphs() {
        int j = 1;
        for (int i = 0; i < lines.length ; i++) {
            if (lines[i].equals(" "))
                j++;
        }

        if (j == 1) {
            double src = lines.length / 5;
            int res = (int)src; //целая часть
            double answer = src - res;
            if (lines.length == 5) {
                return lines.length;
            } else if (answer == 0 || answer < 0.5f) {
                return lines.length / 5;
            } else if (answer != 0 && answer >= 0.5f) {
                return lines.length / 5 + 1;
            }
            return 1;
        } else return j;
    }

    public int getLengthParagraphs(String lines[]) {
        int j = 1;
        for (int i = 0; i < lines.length ; i++) {
            if (lines[i].equals(" "))
                j++;
        }

        if (j == 1) {
            double src = lines.length / 5;
            int res = (int)src; //целая часть
            double answer = src - res;
            if (lines.length == 5) {
                return lines.length;
            } else if (answer == 0 || answer < 0.5f) {
                return lines.length / 5;
            } else if (answer != 0 && answer >= 0.5f) {
                return lines.length / 5 + 1;
            }
            return 1;
        } else return j;
    }

    public Paragraph getParagraph() {
        if (level == countLevels) {
            // ... Делаем что то по окончанию
            // ! переход на интент не работает, надо обрабатовать в классе.

            return null;
        }
        return paragraph[level++];
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

    public void setParagraph(int i, Paragraph p) {
        paragraph[i] = p;
    }

    public Paragraph getParagraph(int i) {
        return paragraph[i];
    }

    public void selectParagraph(int i) {
        selectedParagraph = paragraph[i];
    }
}
