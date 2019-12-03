package com.example.personalcapitalblog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<RssItem> models = new ArrayList<>();
    private Context context;
    private RecyclerViewHolder recyclerViewHolder;
    RelativeLayout relativeLayout;
    public static final int ITEM_VIEW_TYPE_HEADER = 0;
    public static final int ITEM_VIEW_TYPE_LIST = 1;


    public RecyclerViewAdapter(final List<RssItem> items, Context context) {
        if (items != null) {
            this.models.addAll(items);
            this.context = context;
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        relativeLayout = new RelativeLayout(context);
        recyclerViewHolder = new RecyclerViewHolder(relativeLayout, viewType);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        String date = models.get(position).getPubDate();

        new DownloadImageTask(holder.imageView,holder.progressBar).execute(models.get(position).getImageUrl().toString());

        holder.titleView.setText(models.get(position).getTitle());
        holder.descriptionView.setText(date.substring(0,16) +" -- "+ models.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", models.get(position).getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_LIST;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        ProgressBar progressBar;

        public DownloadImageTask(ImageView bmImage,ProgressBar progressBar) {
            this.bmImage = bmImage;
            this.progressBar = progressBar;
        }
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                URL url = new URL(urldisplay);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bmp = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            progressBar.setVisibility(View.INVISIBLE);
            bmImage.setImageBitmap(result);
        }
    }
}