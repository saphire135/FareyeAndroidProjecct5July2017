package com.example.diyanshu.fareyeproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;

import static com.example.diyanshu.fareyeproject.R.id.linearlayoutforlist;
import static com.example.diyanshu.fareyeproject.R.id.url;


/**
 * Created by diyanshu on 10/7/17.
 */

public class Show_Image extends AppCompatActivity {
    View show;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //on creating the app these actions will be performed.
        setContentView(R.layout.list_item);
        Intent intent = getIntent();
        String s = intent.getStringExtra("url");
        show = findViewById(R.id.linearlayoutforlist);

        show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                URL ur;
                try {

                    Databases d = new Databases(getApplicationContext());
//                    Getting gta = new Getting();
                    //       ur = new URL();
                    //    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //  imageView.setImageBitmap(bmp);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);//Logs are used to show what to know whether it got success or it get failed.
                }
            }
        });
    }
}
