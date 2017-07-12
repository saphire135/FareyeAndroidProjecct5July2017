package com.example.diyanshu.fareyeproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivityOn extends AppCompatActivity {

    Button buttn1;
    Getting[] getdata = new Getting[2];//Getting ftype array.
    ListView lv;
    ImageView thumbnailImage;

    @Override
    /**{@link com.example.diyanshu.fareyeproject.R.anim}**/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //on creating the app these actions will be performed.
        setContentView(R.layout.activity_main_on);  //in which layout activity will be performed.


        buttn1 = (Button) findViewById(R.id.button1); //Creating the button with its button id

        //On clicking this button this action will be performed.

        buttn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Databases d3 = new Databases(getApplicationContext());
                d3.deleteall();
                lv = (ListView) findViewById(R.id.listview);
                lv.setAdapter(null);
                Log.d("Success", "database is deleted.");
                //List<Getting> l = new ArrayList<Getting>();
                //lv.setAdapter(new CustomAdapter(getParent(),l));
            }
        });

        final Button buttn2 = (Button) findViewById(R.id.button2);//Creating the button with its button id
        final SwipeDetector swipeDetector = new SwipeDetector();
        buttn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                Databases d = new Databases(MainActivityOn.this);

                int icount = d.getContactsCount();
                //calling http methods.

                if (icount > 0) {
                    Log.d("Success", "Database already have values");
                } else {
                    new HttpHandler().execute("http://jsonplaceholder.typicode.com/photos");
                    Log.d("Success", "Database is updating");

                    //Logs are used to show what to know whether it got success or it get failed.
                    // Code here executes on main thread after user presses button
                }

                lv = (ListView) findViewById(R.id.listview);
                final CustomAdapter customAdapter = new CustomAdapter(MainActivityOn.this, d.getAllContacts());
                lv.setAdapter(customAdapter);

                Log.d("Details", "Fetched");
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        customAdapter.getItem(position);
                        Databases toGetUrl = new Databases(MainActivityOn.this);
                        List<Getting> getUrl = toGetUrl.getAllContacts();
                        String url = getUrl.get(position).getUrl();
                        Intent intent = new Intent(MainActivityOn.this, ShowImage.class);
                        intent.putExtra("url",url );
                        startActivity(intent);

//                        if (swipeDetector.swipeDetected()) {
//                            Log.d("UserViewActivity", "swipe detect calleedddd");
//                            if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
//                                Log.d("UserViewActivity", "swipe calleedddd");
//                            } else {
//
//                            }
//                        } else {
//                        Object o = lv.getItemAtPosition(position);
//                        CustomAdapter str = (CustomAdapter) o;//As yo
// u are using Default String Adapter
//                        Log.d("UserViewActivity", "in onCreate");
////                            Getting getdata = new Getting();
//                        setContentView(R.layout.activity_show_image);
//                        thumbnailImage = (ImageView) findViewById(R.id.thumbnailurl);
//                        Intent intent = new Intent(MainActivityOn.this, ShowImage.class);
//                        intent.putExtra("url",str );
//                        startActivity(intent);
////                            String url = intent.getStringExtra("url");
////                            Picasso.with(MainActivityOn.this).load(url).into(thumbnailImage);
////                        }
//                        /*Object o = lv.getItemAtPosition(position);
//                        CustomAdapter str=(CustomAdapter)o;//As you are using Default String Adapter
//                        Log.d("UserViewActivity", "in onCreate");
//
//                        setContentView(R.layout.activity_show_image);
//                        thumbnailImage = (ImageView) findViewById(R.id.thumbnailurl);
//                        Intent intent = getIntent();
//                        String url = intent.getStringExtra("url");
//                        Picasso.with(MainActivityOn.this).load(url).into(thumbnailImage);*/
                    }
                });

            }
        });

    }

    ProgressBar loadingDatabase;

    class HttpHandler extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loadingDatabase = (ProgressBar) findViewById(R.id.progressBar2);
            loadingDatabase.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... voids) {
            Log.d("HttpHandler", "In doInBAckground()");//Logs are used to show what to know whether it got success or it get failed.
            String email = voids[0];//Creating  a string.
            // Do some validation here


            URL url = null;//Object of url class.
            HttpURLConnection urlConnection = null;//Object of httpurlconnection class.

            try {
                url = new URL(email);//String conversion in url type.
                urlConnection = (HttpURLConnection) url.openConnection();//Urlconnection opened.
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //Getting the BufferedInputStream in the form of Inputstream.
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                //Storing all the elements in stringbuilder.
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();//Closing BufferedReader.
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                getdata = new Getting[jsonArray.length()];


                for (int i = 0; i < jsonArray.length(); i++) {
                    getdata[i] = Getting.fromJson(jsonArray.getJSONObject(i));
                }

                Log.d(getdata[0].getTitle(), "s");  //Edit here.//Logs are used to show what to know whether it got success or it get failed.
                Log.d("Success", jsonArray.getJSONObject(4999).getString("id") + "");//Logs are used to show what to know whether it got success or it get failed.
                return stringBuilder.toString();

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);//Logs are used to show what to know whether it got success or it get failed.
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();//urlconnection disconnected.
            }
            return null;
        }


        protected void onPostExecute(String response) {

            if (getdata.length == 2)
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();

            else {
                Databases db = new Databases(getApplicationContext());
                int c = 0;
                for (Getting gt : getdata) {
                    db.addContact(gt);
                    c++;

                }
                Log.d("dbentries ", c + " ");
            }
        }
    }
}