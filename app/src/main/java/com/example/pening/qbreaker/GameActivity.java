package com.example.pening.qbreaker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.plattysoft.leonids.ParticleSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


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
    ArrayList<Point> Center;
    ArrayList<Rect> SquareInfo;
    ArrayList<Rect> tempSquare;
    int min_size;
    int button_size;
    FrameLayout game_layout;
    FrameLayout.LayoutParams params;
    Canvas tempCanvas;
    TextView score;
    int set_score;
    int Square_number;
    int Color_number;
    boolean isClick;
    Bitmap tempbitmap;
    ParticleSystem particle;

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
        tempSquare = SquareInfo;

        Center = getIntent().getParcelableArrayListExtra("Center");

        min_size = getIntent().getIntExtra("min_size", 0);
        button_size = getIntent().getIntExtra("button_size", 0);
        game_view = (ImageView) findViewById(R.id.gameview);
        stopwatch = (Chronometer) findViewById(R.id.Timer);

        game_layout = (FrameLayout) findViewById(R.id.gamelayout);
        score = (TextView) findViewById(R.id.Score);

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

        this.Change_Color();
        stopwatch.setBase(SystemClock.elapsedRealtime());
        stopwatch.start();


        Toast.makeText(getApplicationContext(), "Game Start", Toast.LENGTH_SHORT).show();
       // while(!(SquareInfo.size() == 0)){
    }
    public void OnClickPause(View v){
        stopwatch.stop();
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
        Boolean combo = false;

        switch (v.getId()){
            case R.drawable.red:
                if(game.isRecentColor(0)){
                    combo = true;
                }
                else{
                    combo = false;
                    Toast.makeText(getApplicationContext(), "MISS", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.drawable.green:
                if(game.isRecentColor(1)){
                    combo = true;
                }
                else{
                    combo = false;
                    Toast.makeText(getApplicationContext(), "MISS", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(), "GREEN", Toast.LENGTH_SHORT).show();
                break;

            case R.drawable.blue:
                if(game.isRecentColor(2)){
                    combo = true;
                }
                else{
                    combo = false;
                    Toast.makeText(getApplicationContext(), "MISS", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getApplicationContext(), "BLUE", Toast.LENGTH_SHORT).show();
                break;
        }
        /*
        particle = new ParticleSystem(this, 40,R.drawable.star_pink,500);
        particle.setScaleRange(0.7f, 0.7f);
        particle.setSpeedRange(0.1f, 0.1f);
        particle.setRotationSpeedRange(90, 180);
        particle.setAcceleration(0.00013f, 90);
        particle.setFadeOut(200, new AccelerateInterpolator());
        int x = DPFromPixel(Center.get(Square_number).x);
        int y = DPFromPixel(Center.get(Square_number).y);

        particle.emit(x,y, 40,1000);
        */


        tempbitmap = game.Delete_Square(SquareInfo,Square_number, tempbitmap);

        Change_Color();
        //game_view.setImageBitmap(tempbitmap);
        //game_view.invalidate();
        game.isCombo(combo);
        set_score = game.Score();
        score.setText(String.valueOf(set_score));
    }
    void Change_Color(){

        if(SquareInfo.size() == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Game Over");

            alert.setPositiveButton("ReStart", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    score.setText(String.valueOf(0));
                    tempbitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
                    tempCanvas = new Canvas(tempbitmap);
                    SquareInfo = tempSquare;
                    game_view.setImageBitmap(tempbitmap);
                    game_view.invalidate();
                    stopwatch.setBase(SystemClock.elapsedRealtime());
                    stopwatch.stop();
                }
            });
            alert.setNegativeButton("to Main", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    Intent intent = new Intent(GameActivity.this, MainActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            alert.show();

            return;
        }
        Square_number =game.Suffle(SquareInfo.size());
        Color_number = game.Suffle(3);

        Paint paint = new Paint();
        paint.setColor(COLOR_NUMBER[Color_number]);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        tempCanvas.drawRect(SquareInfo.get(Square_number), paint);
        game.setRecentColor(Color_number);
        game_view.setImageBitmap(tempbitmap);
        game_view.invalidate();
    }
    private void QR_InsertandUpdateDatabase(String id, String contents, int square_count, int score, int time){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GameActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0];
                    String contents = (String)params[1];
                    String square_count = (String)params[2];
                    String score = (String)params[3];
                    String time = (String)params[4];

                    String link="http://220.67.128.58/my_album_insert.php";
                    String data  = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("CONTENTS", "UTF-8") + "=" + URLEncoder.encode(contents, "UTF-8");
                    data += "&" + URLEncoder.encode("SQUARE_COUNT", "UTF-8") + "=" + URLEncoder.encode(square_count, "UTF-8");
                    data += "&" + URLEncoder.encode("SCORE", "UTF-8") + "=" + URLEncoder.encode(score, "UTF-8");
                    data += "&" + URLEncoder.encode("TIME", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(id, contents, String.valueOf(square_count), String.valueOf(score), String.valueOf(time));
    }
}
