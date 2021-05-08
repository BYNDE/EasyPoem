package com.example.easypoem;

public class search_output {
    public String title;
    public String author;

    public search_output(String title, String author){

        this.title=title;
        this.author=author;
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

}

