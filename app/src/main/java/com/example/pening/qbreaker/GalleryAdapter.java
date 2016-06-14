package com.example.pening.qbreaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pening on 2016-06-13.
 */
public class GalleryAdapter extends BaseAdapter {

    private ArrayList<String> qr_list;
    public GalleryAdapter(){
        qr_list = new ArrayList<String>();
    }

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
            convertView = inflater.inflate(R.layout.gallery_listview, parent, false);

            // TextView에 현재 position의 문자열 추가
            TextView text = (TextView) convertView.findViewById(R.id.content);
            text.setText(qr_list.get(position));

            // 버튼을 터치 했을 때 이벤트 발생
            Button challenge = (Button) convertView.findViewById(R.id.challenge);
        }
        return convertView;
    }
    public void add(String _msg) {
        qr_list.add(_msg);
    }
    public void remove(int _position) {
        qr_list.remove(_position);
    }

}
