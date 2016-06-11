package com.example.pening.qbreaker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MODE[] = {"Break", "Memorial", "etc"};
    private static final int COLOR[] = {R.drawable.red, R.drawable.green, R.drawable.blue};
    private static final int COLOR_NUMBER[] = {Color.RED, Color.GREEN,Color.BLUE};
    private static final int BREAK = 0;
    private static final int MEMORIAL = 1;
    private static final int ETC = 2;


    ImageView game_view;
    ImageButton button[];
    Bitmap bitmap;
    Game game;
    byte[] arr;
    Chronometer stopwatch;
    AlertDialog.Builder menu;
    ArrayList<Point> ButtonCenter;
    ArrayList<Rect> SquareInfo;
    int min_size;
    int button_size;
    FrameLayout game_layout;
    FrameLayout.LayoutParams params;
    Canvas tempCanvas;
    TextView score;
    int Square_number;
    int Color_number;
    boolean isClick;
    Bitmap tempbitmap;
    Thread suffle_thread;
    android.os.Handler s_handler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        arr = getIntent().getByteArrayExtra("image");

        ButtonCenter = getIntent().getParcelableArrayListExtra("Button");
        SquareInfo = getIntent().getParcelableArrayListExtra("Square");
        min_size = getIntent().getIntExtra("min_size", 0);
        button_size = getIntent().getIntExtra("button_size", 0);
        game_view = (ImageView) findViewById(R.id.gameview);
        stopwatch = (Chronometer) findViewById(R.id.Timer);
        game_layout = (FrameLayout) findViewById(R.id.gamelayout);
        score = (TextView) findViewById(R.id.Score);

        s_handler = new android.os.Handler();

        isClick = false;
        button = new ImageButton[3];

        int scale = 0;
        for (int i = 0; i < 3; i++) {
            button[i] = new ImageButton(this);
            button[i].setBackgroundResource(COLOR[i]);
            scale = DPFromPixel(button_size);
            params = new FrameLayout.LayoutParams(scale, scale);
            params.leftMargin = DPFromPixel(ButtonCenter.get(i).x - (button_size / 2));
            params.topMargin = DPFromPixel(ButtonCenter.get(i).y - (button_size / 2));
            game_layout.addView(button[i], params);
            button[i].setOnClickListener(this);
            button[i].setId(COLOR[i]);
        }

        bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        tempbitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        tempCanvas = new Canvas(tempbitmap);

        game_view.setImageBitmap(tempbitmap);
        game_view.invalidate();

        menu = new AlertDialog.Builder(this);
        menu.setTitle("모드 선택").setPositiveButton("선택", null).setNegativeButton("취소", null).setItems(MODE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Game mode : " + MODE[which], Toast.LENGTH_SHORT).show();
                switch (which) {
                    case BREAK:
                        game = new Break();
                        break;
                    case MEMORIAL:
                        break;
                }
            }
        });
        menu.show();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private int DPFromPixel(int pixel) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (pixel * scale);
    }

    public void OnClickStart(View v){
        suffle_thread = new Thread("Suffle Thread"){
          public void run(){
              Square_number = game.Suffle(SquareInfo.size());
              Color_number = game.Suffle(2);
              Runnable callback = new Runnable() {
                  @Override
                  public void run() {
                      Paint paint = new Paint();
                      paint.setColor(COLOR_NUMBER[Color_number]);
                      paint.setStyle(Paint.Style.FILL_AND_STROKE);
                      tempCanvas.drawRect(SquareInfo.get(Square_number), paint);
                      game.setRecentColor(Color_number);
                      game_view.setImageBitmap(tempbitmap);
                      game_view.invalidate();

                  }
              };
              Message message =  Message.obtain(s_handler, callback);
              s_handler.sendMessage(message);
          }
        };

        stopwatch.start();
        suffle_thread.start();

        Toast.makeText(getApplicationContext(), "Game Start", Toast.LENGTH_SHORT).show();
       // while(!(SquareInfo.size() == 0)){

        }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Game Page", // TODO: Define a title for the content shown.
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
                "Game Page", // TODO: Define a title for the content shown.
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.drawable.red:
                Toast.makeText(getApplicationContext(), "RED", Toast.LENGTH_SHORT).show();
                break;
            case R.drawable.green:
                Toast.makeText(getApplicationContext(), "GREEN", Toast.LENGTH_SHORT).show();
                break;
            case R.drawable.blue:
                Toast.makeText(getApplicationContext(), "BLUE", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
