package com.example.diyanshu.fareyeproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
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

    public ImageView url;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater li = context.getLayoutInflater();
            convertView = li.inflate(R.layout.list_item, null);

            TextView id = (TextView) convertView.findViewById(ident);
            id.setText(String.valueOf(getdata.get(position).getId()));

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(getdata.get(position).getTitle());

            final ImageView imageView = new ImageView(getContext());
            url = (ImageView) convertView.findViewById(R.id.thumbnailurl);

        }
        return convertView;
    }
}