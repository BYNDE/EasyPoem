package com.example.easypoem.learn;

import java.io.Serializable;
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
        if (getLengthParagraphs() != 1 && linearSearch() == 1) {
            String[] tempParagraph = new String[getLengthParagraphs()];
            paragraph = new Paragraph[getLengthParagraphs()];
            tempParagraph[0] = "";

            double src = lines.length / 5;
            int res = (int)src; //целая часть
            double answer = (src - res) * 10;
            if (answer >= 5) {
                answer -= 5;
            }


            int lines_i = 0;
            int paragraph_i = 0;
            int i = 1;

            while (paragraph_i < getLengthParagraphs() - 1) {
                tempParagraph[paragraph_i] += lines[lines_i++] + "\n";
                i++;
                if (lines_i + 1 == lines.length - 1) {
                    if (lines_i == 4 + answer) {
                        paragraph[paragraph_i] = new Paragraph(tempParagraph[paragraph_i++]);
                        tempParagraph[paragraph_i] = "";
                    }
                } else {
                    if (i == 5) {
                        i = 0;
                        paragraph[paragraph_i] = new Paragraph(tempParagraph[paragraph_i++]);
                        tempParagraph[paragraph_i] = "";
                    }
                }
            }
            paragraph[paragraph_i] = new Paragraph(tempParagraph[paragraph_i]);
            countLevels = getLengthParagraphs();
        } else if (getLengthParagraphs() == 1) {
            paragraph = new Paragraph[getLengthParagraphs()];
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
                if (!lines[i].equals(" ")) {
                    tempParagraph[j] += lines[i] + "\n";
                }
            }
            paragraph[j] = new Paragraph(tempParagraph[j]);
            countLevels = getLengthParagraphs();
        }
    }

    public int linearSearch() {
        int j = 1;
        for (int i = 0; i < lines.length ; i++) {
            if (lines[i].equals(" "))
                j++;
        }

        return j;
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
            if (lines.length < 5) {
                return 1;
            }else if (lines.length == 5) {
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
