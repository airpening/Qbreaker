package com.example.pening.qbreaker;


import android.graphics.*;
import android.graphics.Point;
import android.widget.Button;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by pening on 2016-05-23.
 */


public class Scan {

    final int width = 330;
    final int height = 330;
    Mat imgMat;
    Imgproc imgproc;

    Scan(){

    }
    public Bitmap Scaning(Bitmap bitmap){

        return bitmap;
    }

    public void ButtonFind(BitMatrix map, int min_size, ArrayList<android.graphics.Point> ButtonCenter,ArrayList<android.graphics.Point> center ){

        int x = 0;
        int y = 0;
        for(int i = 0;i<center.size();i++){
            x = center.get(i).x;
            y = center.get(i).y;
            if(map.get(x, y)){
                if (map.get(x - min_size, y - min_size) && map.get(x - min_size, y) && map.get(x, y - min_size)
                 && map.get(x + min_size, y - min_size) && map.get(x + min_size, y) && map.get(x + min_size, y + min_size)
                 && map.get(x - min_size, y + min_size) && map.get(x, y + min_size)) {
                    ButtonCenter.add(new Point(x, y));
                }
            }
        }
    }

}
