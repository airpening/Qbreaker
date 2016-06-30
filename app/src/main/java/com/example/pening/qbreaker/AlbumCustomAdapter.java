package com.example.pening.qbreaker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import com.example.pening.qbreaker.Data;

/**
 * Created by pening on 2016-06-13.
 */
public class AlbumCustomAdapter extends BaseAdapter {

    private ArrayList<Data> qr_list;

    public AlbumCustomAdapter(){
        qr_list = new ArrayList<Data>();
    }
    String contents;

    @Override
    public int getCount() {
        return qr_list.size();
    }

    @Override
    public Object getItem(int position) {
        return qr_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if (convertView == null) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);

            // TextView에 현재 position의 문자열 추가
            TextView text = (TextView) convertView.findViewById(R.id.contents);
            TextView bit_count = (TextView) convertView.findViewById(R.id.album_bitcount);
            TextView score = (TextView) convertView.findViewById(R.id.album_score);
            TextView time = (TextView) convertView.findViewById(R.id.album_time);


            text.setText(qr_list.get(position).getContents());
            contents = qr_list.get(position).getContents();
            bit_count.setText(String.valueOf(qr_list.get(position).getBitcount()));
            score.setText(String.valueOf(qr_list.get(position).getScore()));
            time.setText(String.valueOf(qr_list.get(position).getTime()));


            // 버튼을 터치 했을 때 이벤트 발생
            Button challenge = (Button) convertView.findViewById(R.id.btn_test);
            Button qr_upload = (Button) convertView.findViewById(R.id.upload);

            challenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);

                    intent.putExtra("contents", contents);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    context.startActivity(intent);
                }
            });

            qr_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }
        return convertView;
    }
    public void add(Data data) {
        qr_list.add(data);
    }
    public void remove(int _position) {
        qr_list.remove(_position);
    }
    private void QR_insertToDatabase(String id, ViewGroup parent){

        final Context context = parent.getContext();
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0];

                    String link="http://220.67.128.58/gallery_insert.php";
                    String data  = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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
        task.execute(id);
    }

}
