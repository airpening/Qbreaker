package com.example.pening.qbreaker;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by pening on 2016-06-24.
 */
public class Memorial extends Game {

    @Override
    int Suffle(int number) {
        return 0;
    }

    @Override
    void Timer() {

    }

    @Override
    int Score() {
        return 0;
    }

    @Override
    void isCombo(Boolean Combo) {

    }

    @Override
    Bitmap Delete_Square(ArrayList<Rect> SquareInfo, int Square_number, Bitmap bitmap) {
        return null;
    }

    @Override
    Boolean isRecentColor(int Colornumber) {
        return null;
    }

    @Override
    void setRecentColor(int Colornumber) {

    }
}
