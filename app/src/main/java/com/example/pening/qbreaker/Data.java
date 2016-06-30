package com.example.pening.qbreaker;

/**
 * Created by pening on 2016-06-28.
 */
public class Data {
    private String contents;
    private int bitcount;
    private int score;
    private int time;

    public void Data(){
        contents = null;
        bitcount = 0;
        score = 0;
        time = 0;
    }
    public void setData(String contents, int bitcount, int score, int time){
        this.contents = contents;
        this.bitcount = bitcount;
        this.score = score;
        this.time = time;
    }
    public String getContents(){
        return contents;
    }
    public int getBitcount(){
        return bitcount;
    }
    public int getScore(){
        return score;
    }
    public int getTime(){
        return time;
    }
}
