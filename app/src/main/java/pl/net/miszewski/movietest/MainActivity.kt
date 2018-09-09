package pl.net.miszewski.movietest

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.settings.apply {
            javaScriptEnabled = true
            allowContentAccess = true
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            domStorageEnabled = true
            pluginState = WebSettings.PluginState.ON
            setAppCacheEnabled(true)
            setAppCachePath(applicationContext.filesDir.absolutePath + "/cache")
            databaseEnabled = true
            databasePath = applicationContext.filesDir.absolutePath + "/databases"
            mediaPlaybackRequiresUserGesture = false
        }
        WebView.setWebContentsDebuggingEnabled(true)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
                Log.d("console", "${cm.message()}, ${cm.lineNumber()}, ${cm.sourceId()}")
                return true
            }
        }
        webView.loadUrl("file:///android_asset/player.html")

        webView.webViewClient = object : WebViewClient() {
            // autoplay when finished loading via javascript injection
            override fun onPageFinished(view: WebView, url: String) {
                webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onResume() {
        webView.onResume()
        super.onResume()
    }

}
