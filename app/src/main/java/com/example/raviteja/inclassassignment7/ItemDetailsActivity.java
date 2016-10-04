package com.example.raviteja.inclassassignment7;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemDetailsActivity extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        setTitle("iTunes Top Podcasts");
        TextView title_text = (TextView) findViewById(R.id.title_tv);
        TextView date_text = (TextView) findViewById(R.id.date_tv);
        TextView description_text = (TextView) findViewById(R.id.Description_tv);
        image = (ImageView) findViewById(R.id.imageView);


        Items Item = null;
        final WebView webView = new WebView(this);
        if (getIntent().getExtras() !=null) {
            Bundle b = getIntent().getExtras();
            Item = (Items) b.get("ITEM_KEY");
            title_text.setText(Item.getTitle());
            Picasso.with(ItemDetailsActivity.this).load(Item.getLargeImageUrl()).into(image);

           date_text.setText(Item.getReleaseDate());
            description_text.setText(Item.getSummary());
        }

        final Items finalItem = Item;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = finalItem.getLargeImageUrl();
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(url);
                webView.setWebViewClient(new MyWebViewClient());

            }
        });

    }
    class GetImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                image.setImageBitmap(bitmap);
            }
        }
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}


