package com.jfinkelstudio.practice1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBrowser extends AppCompatActivity {

    MenuActivity myMenuActivity =  new MenuActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
            WebView webView = findViewById(R.id.webBrowser);
              myMenuActivity.setWebBrowserWebView(webView);
    }


}
