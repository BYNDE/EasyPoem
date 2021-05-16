package com.example.easypoem;

public class search_output {
    public String author;
    public Long id;
    public String text;
    public String title;
    public String name;

    public search_output() {}

    public search_output(String title, String author,String text, Long id){
        this.title=title;
        this.author=author;
        this.text = text;
        this.id = id;
    }

}

