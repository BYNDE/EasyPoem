package com.example.easypoem;

public class search_database {
    private String title;
    private String author;

    public search_database(String title,String author){
        this.setTitle(title);
        this.setAuthor(author);
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
}
