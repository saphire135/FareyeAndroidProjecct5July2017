package com.example.diyanshu.fareyeproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ShowImage extends AppCompatActivity {

    ImageView thumbnailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);


//        Log.d("UserViewActivity", "in onCreate");
//        super.onCreate(savedInstanceState);
        thumbnailImage = (ImageView) findViewById(R.id.thumbnailurl);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Picasso.with(this).load(url).into(thumbnailImage);



//                thumbnailImage.setImageDrawable(R.id.thumbnailurl);

    }
}
