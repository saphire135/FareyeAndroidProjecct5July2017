package com.example.diyanshu.fareyeproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivityOn extends AppCompatActivity {

    Button buttn1;
    Getting[] getdata = new Getting[2];//Getting ftype array.
    ListView lv;

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
                //List<Getting> l = new ArrayList<Getting>();
                //lv.setAdapter(new CustomAdapter(getParent(),l));
            }
        });

        final Button buttn2 = (Button) findViewById(R.id.button2);//Creating the button with its button id
        buttn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new HttpHandler().execute("http://jsonplaceholder.typicode.com/photos");//calling http methods.

                Databases d = new Databases(MainActivityOn.this);
                lv = (ListView) findViewById(R.id.listview);

                lv.setAdapter(new CustomAdapter(MainActivityOn.this, d.getAllContacts()));

                Log.d("Details", "HI");//Logs are used to show what to know whether it got success or it get failed.
                // Code here executes on main thread after user presses button
            }
        });

    }

    class HttpHandler extends AsyncTask<String, String, String> {


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