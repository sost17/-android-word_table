package com.col.commo.word_table;

/**
 * Created by commo on 2017/5/1.
 */

public class Word {

    private String word;
    private String mean;
    private String user;

    public Word(){

    }
    public Word(String word, String mean,String user){
        this.user = user;
        this.word = word;
        this.mean = mean;
    }

    public String getWord() {
        return word;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMean(String mean) {

        this.mean = mean;
    }

    public void setWord(String word) {

        this.word = word;
    }

    public String getUser() {

        return user;
    }

    public String getMean() {

        return mean;
    }
}
