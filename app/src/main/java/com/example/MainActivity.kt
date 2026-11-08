package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        CandyBuddiesGameApp()
      }
    }
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CandyBuddiesGameApp() {
  val context = LocalContext.current
  val webView = remember {
    WebView(context).apply {
      layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
      )
      settings.apply {
        javaScriptEnabled = true
        domStorageEnabled = true
        databaseEnabled = true
        mediaPlaybackRequiresUserGesture = false
        allowFileAccess = true
        cacheMode = WebSettings.LOAD_DEFAULT
        useWideViewPort = true
        loadWithOverviewMode = true
        setSupportZoom(false)
        displayZoomControls = false
      }
      overScrollMode = View.OVER_SCROLL_NEVER
      isVerticalScrollBarEnabled = false
      isHorizontalScrollBarEnabled = false
      webChromeClient = WebChromeClient()
      webViewClient = WebViewClient()
      setBackgroundColor(android.graphics.Color.TRANSPARENT)
      loadUrl("file:///android_asset/index.html")
    }
  }

  BackHandler(enabled = true) {
    if (webView.canGoBack()) {
      webView.goBack()
    }
  }

  DisposableEffect(Unit) {
    onDispose {
      webView.destroy()
    }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFFF9EBB))
      .statusBarsPadding()
  ) {
    AndroidView(
      factory = { webView },
      modifier = Modifier.fillMaxSize()
    )
  }
}
