package com.example.pening.qbreaker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by pening on 2016-06-07.
 */

public class Break extends Game{
    private int COLOR[] = {Color.RED,Color.GREEN,Color.BLUE};

    private int time ;
    private int score;
    private int combo;
    private int recentColor;


    public Break(){
        time = 0;
        score = 0;
        combo = 0;
    }
    public int Suffle(int number) {
        return (int)(Math.random() * number);
    }
    public int  Score(){
        return score = combo + score;
    }
    void isCombo(Boolean Combo) {
        if(Combo){
            combo++;
        }
        else{
            combo = 0;
        }
    }

    public void Timer(){

    }
    public Bitmap Delete_Square(ArrayList<Rect> SquareInfo,int Square_number, Bitmap bitmap){

        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Canvas canvas = new Canvas(bitmap);

        canvas.drawRect(SquareInfo.get(Square_number), paint);
        SquareInfo.remove(Square_number);
        return bitmap;
    }

    @Override
    Boolean isRecentColor(int Color_number) {

        return recentColor == COLOR[Color_number];
    }

    @Override
    void setRecentColor(int Color_number) {
        recentColor = COLOR[Color_number];
    }


}
