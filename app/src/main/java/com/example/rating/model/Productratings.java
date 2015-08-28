package com.example.rating.model;

/**
 * Created by Gopinath on 8/26/2015.
 */
public class Productratings {
    int id;
    int productid;
    int year;
    int month;
    Double rating;
    String comments;
    public Productratings(){

    }
    public Productratings(int id,int productid,Double rating,String comments,int month,int year){
        this.id=id;
        this.productid=productid;
        this.rating=rating;
        this.comments=comments;
        this.month=month;
        this.year=year;
    }
    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setProductid(int productid) {
        this.productid =productid;
    }
    public void setRating(Double rating) {
        this.rating =rating;
    }
    public void setComments(String comments) {
        this.comments =comments;
    }
    public void setMonth(int month){this.month=month;}
    public void setYear(int year){this.year=year;}

    // getter
    public int getId() {
        return this.id;
    }
    public int getProductid() {
        return this.productid;
    }

    public Double getRating() {
        return this.rating;
    }
    public String getComments() {
        return this.comments;
    }
    public int getMonth(){return this.month;}
    public int getYear(){return this.year;}
}
