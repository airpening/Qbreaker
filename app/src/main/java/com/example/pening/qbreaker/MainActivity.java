package com.example.pening.qbreaker;

import android.content.Intent;
import android.graphics.*;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Vector;



public class MainActivity extends AppCompatActivity {
    IntentIntegrator integrator = new IntentIntegrator(this);
    final int width = 330;
    final int height = 330;
    Bitmap bitmap;
    ImageView view;
    TextView text;
    Mat imgMat;
    Scan scan;
    BitMatrix bytemap;
    private int size = 0;
    private int min_size = 330;
    private int button_size = 0;
    ArrayList<android.graphics.Point> center;
    ArrayList<android.graphics.Point> ButtonCenter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void OnClickScan(View v) {
        integrator.initiateScan();
    }
    public void OnClickContour(View v){
        center = new ArrayList<>();
        ButtonCenter = new ArrayList<>();
        scan = new Scan();
        for (int i = 0 ; i < width; i = i+min_size) {
            for (int j = 0; j < height; j = j+min_size) {
                if(bytemap.get(i,j)) {

                    if((min_size/2)%2 == 1) {
                        bitmap.setPixel(i + min_size / 2, j + min_size / 2, Color.GREEN);
                        center.add(new android.graphics.Point(i + min_size / 2, j + min_size / 2));
                    }
                    else {
                        bitmap.setPixel(i - min_size / 2, j - min_size / 2, Color.GREEN);
                        center.add(new android.graphics.Point(i - min_size/2, j - min_size/2));
                    }
                }
            }
        }
        scan.ButtonFind(bytemap,min_size,ButtonCenter, center);
        for(int i = 0;i<ButtonCenter.size();i++){
            bitmap.setPixel(ButtonCenter.get(i).x,ButtonCenter.get(i).y,Color.RED);
        }
        view.setImageBitmap(bitmap);
        view.invalidate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = integrator.parseActivityResult(requestCode, resultCode, data);
        MultiFormatWriter gen = new MultiFormatWriter();
        view  = (ImageView)findViewById(R.id.imageView);
        text = (TextView)findViewById(R.id.textView);
        min_size = 330;
        size = 0;

        try {
            bytemap = gen.encode(result.getContents(), BarcodeFormat.QR_CODE, width, height);
            //Button pixel[] = new Button[250];

            bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
            boolean flag = false;
            for (int i = 0 ; i < width; ++i) {
                for (int j = 0; j < height; ++j) {
                    if(bytemap.get(j,i)){
                        bitmap.setPixel(j,i,Color.BLACK);
                        size++;
                        flag = true;
                    }
                    else{
                        bitmap.setPixel(j,i,Color.WHITE);
                        if(min_size >= size && flag){
                            min_size = size;
                            size = 0;
                            flag = false;
                        }
                    }
                }
            }
            view.setImageBitmap(bitmap);
            view.invalidate();
            text.setText(result.getContents() + " [" + result.getFormatName() + "]" + " [" + result.getErrorCorrectionLevel() + "]" + "[" + result.getOrientation() + "]  pixel size ="  + min_size);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                    imgMat=new Mat();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.pening.qbreaker/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.pening.qbreaker/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
