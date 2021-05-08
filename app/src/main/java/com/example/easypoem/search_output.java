package com.example.easypoem;

public class search_output {
    public String title;
    public String author;
    public String text;

    public search_output(String title, String author,String text){

        this.title=title;
        this.author=author;
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String author) {
        this.author = author;
    }

}

