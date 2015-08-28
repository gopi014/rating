package com.example.rating.model;

/**
 * Created by Gopinath on 8/26/2015.
 */
public class Users {
    int _id;
    String mailid;
    public Users() {
    }
    public Users(String mailid){

        this.mailid = mailid;
    }
    public Users(int id, String mailid){
        this._id = id;
        this.mailid = mailid;
    }
    // setters
    public void setId(int id) {
        this._id = id;
    }

    public void setMail(String mailid) {
        this.mailid = mailid;
    }
    // getters
    public long getId() {
        return this._id;
    }

    public String getmail() {
        return this.mailid;
    }

}


