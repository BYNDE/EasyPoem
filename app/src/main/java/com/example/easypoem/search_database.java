package com.example.easypoem;

public class search_database {
    private String title;
    private String author;
    private String text;

    public search_database() {}

    public search_database(String title,String author, String text){
        this.setTitle(title);
        this.setAuthor(author);
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
