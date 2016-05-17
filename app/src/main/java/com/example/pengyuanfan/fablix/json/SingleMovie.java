package com.example.pengyuanfan.fablix.json;

import java.util.List;

/**
 * Created by pengyuanfan on 5/14/2016.
 */
public class SingleMovie {

    private String dirctor;
    private String trailer;
    private String year;
    private String price;
    private String genre;
    private String banner_url;
    private String id;
    private List<Star> stars;
    private String title;

    public String getDirctor() {
        return dirctor;
    }

    public void setDirctor(String dirctor) {
        this.dirctor = dirctor;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
