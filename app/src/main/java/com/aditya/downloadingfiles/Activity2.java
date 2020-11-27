package com.aditya.downloadingfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Activity2 extends AppCompatActivity {

    Bitmap myBitmap;
    ImageView imageView;

    //creating a method to handel the execution of codes when the user taps on the download button
    public void DownloadImage(View view)
    {
        Log.i("button", "Download button tapped");
        //calling imageDownloader class in the DownloadImage method by creating the object of the class
        //create an object of the imageDownloader class
        ImageDownloader task = new ImageDownloader();
        /*
        now we will execute the imageDownloader class by the use of its object task
        method to execute a class is (object of the class).execute("url of the site");
        this url of the website now gets passed to the protected String imageDownloader(String... urls) method
        inside the imageDownloader class
         */
        Bitmap myImage;
        //signing myImage variable to the task that we just created
        //.get() will return the actual bitmap returned by the class ImageDownloader
        try {
            myImage = task.execute("https://i.ytimg.com/vi/UuTq-Aynwnw/maxresdefault.jpg").get();
            //setting imageView to the downloaded image by the bitmap
            imageView.setImageBitmap(myImage);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //creating a new class for managing downloads in the background thread
    //it borrows from the existing class the AsyncTask
    //anything written inside the DownloadTask class will happen in the background
    /*
    AsyncTask<String_i, void, Bitmap>
    String_i allows us to pass in the string(url) Bitmap allows us to pass out the downloaded image from the DownloadTask class
    the place where the void is written there we can specify the functionality if the class
     */
     public class ImageDownloader extends AsyncTask<String,Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection;
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                //inputStream will gather the data as its coming through
                InputStream in = httpURLConnection.getInputStream();
                //InputStreamReader reads the data that is coming in through the InputStream
                InputStreamReader reader = new InputStreamReader(in); //we have pass in the inputstream in here
                /*
                      it will give us back an image
                      this is its way of giving us image after fetching that image from the internet
                 */
                myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap; //it return the image
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        imageView = (ImageView)findViewById(R.id.imageView);

    }
}