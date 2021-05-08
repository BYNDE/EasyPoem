package com.example.easypoem;

public class search_output {
    public String author;
    public Long id;
    public String text;
    public String title;

    public search_output() {}

    public search_output(String title, String author,String text, Long id){

        this.title=title;
        this.author=author;
        this.text = text;
        this.id = id;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

