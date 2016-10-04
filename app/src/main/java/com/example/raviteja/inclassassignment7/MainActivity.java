package com.example.raviteja.inclassassignment7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Top Podcasts");
        new GetDataAsyncTask(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/xml");
    }
}
