package com.col.commo.word_table;

/**
 * Created by commo on 2017/4/30.
 */

public class Users {

    private String user;
    private String password;

    public Users(){

    }
    public Users(String user, String password){
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {

        this.user = user;
    }


}
