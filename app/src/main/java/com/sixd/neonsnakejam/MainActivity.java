package com.sixd.neonsnakejam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
 private WebView webView;
 private long lastBackPress=0;
 @SuppressLint("SetJavaScriptEnabled")
 @Override public void onCreate(Bundle b) {
  super.onCreate(b);
  getWindow().setStatusBarColor(Color.BLACK);
  getWindow().setNavigationBarColor(Color.BLACK);
  webView=new WebView(this); webView.setBackgroundColor(Color.BLACK); setContentView(webView);
  WebSettings s=webView.getSettings();
  s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true);
  s.setMediaPlaybackRequiresUserGesture(false); s.setAllowFileAccess(true);
  webView.setWebViewClient(new WebViewClient()); webView.setWebChromeClient(new WebChromeClient());
  webView.loadUrl("file:///android_asset/index.html");
 }
 @Override protected void onPause(){ if(webView!=null)webView.evaluateJavascript("window.androidPauseAudio&&window.androidPauseAudio()",null); super.onPause(); }
 @Override protected void onResume(){ super.onResume(); if(webView!=null)webView.evaluateJavascript("window.androidResumeAudio&&window.androidResumeAudio()",null); }
 @Override protected void onDestroy(){ if(webView!=null){webView.evaluateJavascript("window.androidStopAudio&&window.androidStopAudio()",null);webView.destroy();} super.onDestroy(); }
 @Override public void onBackPressed(){
  long now=SystemClock.elapsedRealtime();
  if(now-lastBackPress<2200){
   if(webView!=null)webView.evaluateJavascript("window.androidStopAudio&&window.androidStopAudio()",null);
   finishAndRemoveTask();
  }else{
   lastBackPress=now;
   Toast.makeText(this,"Pulsa ATRÁS otra vez para salir",Toast.LENGTH_SHORT).show();
  }
 }
}