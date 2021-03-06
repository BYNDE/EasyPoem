
package com.example.easypoem.HttpClient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoemModel {

    public PoemModel() {}

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("author")
    @Expose
    private String author;

    public PoemModel(String title, String author,String text, Integer id) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
