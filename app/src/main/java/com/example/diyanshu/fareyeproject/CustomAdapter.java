package com.example.diyanshu.fareyeproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.diyanshu.fareyeproject.R.id.ident;

/**
 * Created by diyanshu 09-07-2017.
 */
//for listview rows
public class CustomAdapter extends ArrayAdapter<String> {
    Activity context;
    List<Getting> getdata;

    CustomAdapter(Activity context, List<Getting> getdata) {

        super(context, android.R.layout.simple_list_item_1, new String[getdata.size()]);
        this.context = context;
        this.getdata = getdata;

    }

    TextView url;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater li = context.getLayoutInflater();
            convertView = li.inflate(R.layout.list_item, null);

            TextView id = (TextView) convertView.findViewById(ident);
            id.setText(String.valueOf(getdata.get(position).getId()));

            final ImageView imageView = new ImageView(getContext());
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(getdata.get(position).getTitle());
            url = (TextView) convertView.findViewById(R.id.url);
            url.setText(getdata.get(position).getUrl());
            url.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, Show_Image.class);
                    intent.putExtra("url", url.getText());
                    context.startActivity(intent);
                }
            });

        }
        return convertView;
    }
}