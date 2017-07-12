package com.example.diyanshu.fareyeproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ShowImage extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);


//        Log.d("UserViewActivity", "in onCreate");
//        super.onCreate(savedInstanceState);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.d("url", url);
//        InputStream stream = new ByteArrayInputStream(url.getBytes());
//        Bitmap bmp = BitmapFactory.decodeStream(stream);
//        imageView.setImageBitmap(bmp);
        Picasso.with(this).load(url).into(imageView);

//        Picasso.with(this).load(url).into(thumbnailImage);
//             thumbnailImage.setImageDrawable(R.id.thumbnailurl);
    }
}
