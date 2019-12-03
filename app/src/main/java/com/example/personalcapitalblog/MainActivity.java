package com.example.personalcapitalblog;

import android.app.Activity;
import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends Activity {
    private static final String URL = "https://www.personalcapital.com/blog/feed/?cat=3,891,890,68,284";
    XmlParser xmlParser = new XmlParser();
    List<RssItem> items = null;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout myLayout = new RelativeLayout(this);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams progressParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        progressParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(progressParam);

        downloadAndParseXml();
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
        recyclerView = new RecyclerView(this);
        recyclerView.setClipToPadding(false);
        recyclerView.setHasFixedSize(true);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            gridLayoutManager = new GridLayoutManager(this, 3);
        } else {
            gridLayoutManager = new GridLayoutManager(this, 2);
        }

        recyclerView.setLayoutManager(gridLayoutManager);
        myLayout.addView(progressBar);
        myLayout.addView(recyclerView, params);
        setContentView(myLayout);

    }

    public void downloadAndParseXml() {
        new DownloadAndParseTask().execute(URL);
    }

    private class DownloadAndParseTask extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... urls) {
            try {
                downloadXml(urls[0]);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            final RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, MainActivity.this);
            recyclerView.setAdapter(adapter);
            // get span length for header row
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return adapter.isHeader(position) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }

    }

    private void downloadXml(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;

        try {
            stream = downloadUrl(urlString);
            items = xmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        java.net.URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

}