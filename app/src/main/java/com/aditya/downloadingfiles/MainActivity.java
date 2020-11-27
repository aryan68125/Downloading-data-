package com.aditya.downloadingfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //creating a new class for managing downloads in the background thread
    //it borrows from the existing class the AsyncTask
    //anything written inside the DownloadTask class will happen in the background
    /*
    AsyncTask<String_i, void, String_ii>
    String_i allows us to pass in the string string_ii allows us to pass out the string from the DownloadTask class
    the place where the void is written there we can specify the functionality if the class
     */
    public class DownloadTask extends AsyncTask<String, Void, String> {
        /*
        doInBackground(String... strings) //String...strings allows us to as many strings as we like to them it basically acts
        as an array but its not an array
         */
        @Override //this method will run the codes written inside in the background
        protected String doInBackground(String... urls) {
            String result = null;
            //creating a url variable
            URL url;
            /*
            HttpURLConnection does the same thing as a browser would we pass the url to this method nd it fetched the informaton from the web
            although it will only able to fetch the text from the internet
             */
            HttpURLConnection urlConnection;

            try {
                url = new URL(urls[0]); //converting strings into url
                urlConnection = (HttpURLConnection) url.openConnection(); //opening connection to that url
                //inputStream will gather the data as its coming through
                InputStream in = urlConnection.getInputStream();
                //InputStreamReader reads the data that is coming in through the InputStream
                InputStreamReader reader = new InputStreamReader(in); //we have pass in the inputstream in here
                /*
                      it will give us back an int
                      this is its way of giving us each individual character
                      when we go through the InputStream we have to go through it character by character
                 */
               int data = reader.read();
                while(data != -1)  //if data becomes -1 then its reader's way of saying that we are done that was the last data that i read there is no more to read anything
                {
                    //convert int data into character data
                    char current = (char) data;
                    //adding character current to the String result
                    result +=current;
                    data = reader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Failed";
            } catch (IOException e) {
                e.printStackTrace();
                return "failed!";
            }

             return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.textView);

        //create an object of the DownloadTask class
        DownloadTask task = new DownloadTask();
        /*
        now we will execute the downloadTask class by the use of its object task
        method to execute a class is (object of the class).execute("url of the site");
        this url of the website now gets passed to the protected String doInBackground(String... strings) method
        inside the DownloadTask class
         */
        String result=null; //this will hold the returned value "done!" from the DownloadTask class
        try {
            result = task.execute("https://www.zappycode.com/").get(); //.get() will get the returned value from the downloadTask class
        }
        catch(Exception e)
        {
            e.printStackTrace(); // this will print out and log whatever the error there is
        }
        Log.i("result",result);
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        textView.setText(result);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity2.class);
                startActivity(intent);
            }
        });
    }

    }
