package com.example.personalcapitalblog;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import static com.example.personalcapitalblog.RecyclerViewAdapter.ITEM_VIEW_TYPE_HEADER;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView titleView;
    public TextView descriptionView;
    public TextView headerView;

    public ProgressBar progressBar;

    RecyclerViewHolder(View itemView, int viewType) {
        super(itemView);

        Context context = itemView.getContext();
        RelativeLayout relativeLayout = (RelativeLayout) itemView;

        headerView = new TextView(context);
        int headId = View.generateViewId();
        headerView.setId(headId);
        headerView.setText("Research & Insights");
        headerView.setTextSize(30);
        headerView.setPadding(25, 25, 25, 25);
        headerView.setGravity(Gravity.CENTER);

        imageView = new ImageView(context);
        int imageId = View.generateViewId();
        imageView.setId(imageId);
        imageView.setCropToPadding(true);
        imageView.setAdjustViewBounds(true);
        imageView.setPadding(25, 25, 25, 25);
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.BELOW, headerView.getId());
        relativeLayout.addView(imageView,imageParams);

        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
        progressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams progressParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        progressParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setVisibility(View.INVISIBLE);
        relativeLayout.addView(progressBar,progressParam);


        titleView = new TextView(context);
        int titleId = View.generateViewId();
        titleView.setId(titleId);
        titleView.setPadding(25, 25, 25, 25);

        titleView.setEllipsize(TextUtils.TruncateAt.END);
        if(viewType == ITEM_VIEW_TYPE_HEADER){
            titleView.setSingleLine(true);
            titleView.setLines(1);
        }
        else{
            titleView.setSingleLine(false);
            titleView.setLines(2);
        }
        titleView.setTextSize(20);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.BELOW, imageView.getId());
        relativeLayout.addView(titleView,titleParams);


        descriptionView = new TextView(context);
        int descId = View.generateViewId();
        descriptionView.setId(descId);
        descriptionView.setSingleLine(false);
        descriptionView.setEllipsize(TextUtils.TruncateAt.END);
        descriptionView.setLines(2);
        descriptionView.setTextSize(15);
        descriptionView.setPadding(25, 25, 25, 25);

        RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        descParams.addRule(RelativeLayout.BELOW, titleView.getId());
        if(viewType == ITEM_VIEW_TYPE_HEADER){
            relativeLayout.addView(headerView);
            relativeLayout.addView(descriptionView,descParams);

        }

        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(10, 0xFF000000); //black border with full opacity
        border.setSize(30, 30);
        relativeLayout.setBackground(border);

    }
}
