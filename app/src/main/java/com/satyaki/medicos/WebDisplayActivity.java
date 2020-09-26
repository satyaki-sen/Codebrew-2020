package com.satyaki.medicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebDisplayActivity extends AppCompatActivity {

    String linkURL;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_display);
        linkURL=getIntent().getStringExtra("Link");
        webView=findViewById(R.id.web_View);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(linkURL);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            Intent intent=new Intent(WebDisplayActivity.this,DoctorsActivity.class);
            startActivity(intent);
            finish();
        }
       // super.onBackPressed();
    }
}
