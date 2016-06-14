package com.example.pening.qbreaker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GalleryActivity extends AppCompatActivity {
    private ListView my_Listview;
    private GalleryAdapter my_Adapter;
    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_CONTENTS = "CONTENTS";
    private static final String TAG_SQUARE = "SQUARE_COUNT";
    private static final String TAG_SCORE ="SCORE";
    private static final String TAG_TIME = "TIME";

    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        String id = new String();
        id = "pening";

        my_Adapter = new GalleryAdapter();

        my_Listview = (ListView) findViewById(R.id.gallerylistview);

        my_Listview.setAdapter(my_Adapter);

        getData("http://220.67.128.58/test.php");

    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String contents = c.getString(TAG_CONTENTS);
                int square_count = c.getInt(TAG_SQUARE);
                int score = c.getInt(TAG_SCORE);
                int time = c.getInt(TAG_TIME);

                my_Adapter.add(contents +"\n bit count = "+ square_count+ " score = " +score +" time = "+time);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
