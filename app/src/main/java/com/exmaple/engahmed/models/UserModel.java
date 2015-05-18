package com.exmaple.engahmed.models;

/**
 * Created by Ahmed on 5/15/2015.
 */
public class UserModel {
    int ID;
    String username;
    String password;
    public void setID(int id){
        ID = id;
    }
    public int getID()
    {
        return ID;
    }
    public void setUsername(String si){
        username = si;
    }
    public String getUsername()
    {
        return username;
    }
    public void setPassword(String body){
        this.password = body;
    }
    public String getPassword()
    {
        return password;
    }
}
