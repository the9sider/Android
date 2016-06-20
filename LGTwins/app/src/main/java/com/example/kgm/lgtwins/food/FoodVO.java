package com.example.kgm.lgtwins.food;

/**
 * Created by KGM on 2016-06-20.
 */
public class FoodVO {

    private int src;
    private float rating;
    private String title;
    private String menu;
    private String location;

    public FoodVO(int src, String title, String menu, String location) {
        this.src = src;
        this.rating = 0.0f;
        this.title = title;
        this.menu = menu;
        this.location = location;
    }

    public int getSrc() {
        return src;
    }

    public float getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getMenu() {
        return menu;
    }

    public String getLocation() {
        return location;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
