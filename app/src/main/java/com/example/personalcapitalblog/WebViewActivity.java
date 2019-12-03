package com.example.personalcapitalblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class WebViewActivity extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout layout = new RelativeLayout(this);

        String url = getIntent().getStringExtra("url") + "?displayMobileNavigation=0";

        myWebView = new WebView(this);
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    WebViewActivity.this.setTitle(title);
                }
            }
        });
        myWebView.loadUrl(url);
        layout.addView(myWebView);
        setContentView(layout);

    }
}
