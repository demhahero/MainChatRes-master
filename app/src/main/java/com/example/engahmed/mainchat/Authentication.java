package com.example.engahmed.mainchat;

import com.exmaple.engahmed.models.UserModel;

import java.util.HashMap;

/**
 * Created by EngAhmed on 03/05/2015.
 */
public class Authentication {
    DataTaker dt;

    public Authentication(DataTaker dt){
        this.dt = dt;
    }

    public boolean login(UserModel um) {
        if(dt.doLogin(um))
            return true;
        else
            return false;
    }

    public  UserModel getUserInfo(){
        return dt.getCurrentUser();
    }

    public boolean logout() {
        boolean res = dt.doLogout();

        if(dt.getCurrentUser() == null && res)
            return true;
        else
            return false;
    }

    public UserModel[] getFirendList() {
        UserModel[] friends;
        friends = dt.getFriendsOf(dt.getCurrentUser());
        return friends;
    }

    public boolean addNewUser(UserModel um) {
        boolean res = dt.addNewUser(um);
        return res;
    }
}
