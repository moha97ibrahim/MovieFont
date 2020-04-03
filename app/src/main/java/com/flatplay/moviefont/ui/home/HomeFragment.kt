package com.flatplay.moviefont.ui.home

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.flatplay.moviefont.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var URL: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = context!!.getSharedPreferences("URL", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()



        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        val webView = root.findViewById<WebView>(R.id.webView)


//        if (sharedPreferences.getString("URL", null) != null)
//            webView.loadUrl(sharedPreferences.getString("URL", null))
//        else
//            webView.loadUrl("http://lifeliker.net/")

        webView.loadUrl("http://lifeliker.net/")

        webView.webViewClient = MyWebViewClient(this)

        val setting = webView.settings

        setting.javaScriptEnabled = true

        setting.setAppCacheEnabled(true)
        setting.cacheMode = WebSettings.LOAD_DEFAULT

        setting.setSupportZoom(false)

        setting.domStorageEnabled = true
        setting.setSupportMultipleWindows(true)
        setting.loadWithOverviewMode = true
        setting.allowContentAccess = true
        setting.setGeolocationEnabled(true)
        setting.allowUniversalAccessFromFileURLs = true
        setting.allowFileAccess = true

        webView.isHorizontalScrollBarEnabled = false




        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                // Page loading started
                // Do something
                Log.e("loading", "" + url)
                editor.putString("URL", url)
                editor.apply()
                editor.commit()


            }

            override fun onPageFinished(view: WebView, url: String) {
                // Page loading finished
                // Display the loaded page title in a toast message
                Log.e("loaded", "loaded")
            }
        }

        val folder : File = context!!.filesDir//Creating an internal dir;

        val file : File = File(folder,"Life Liker")
        if (!file.exists()) {
            Toast.makeText(context, "New Folder Created", Toast.LENGTH_LONG).show()
            file.mkdirs()
        }

        webView.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading file...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                ".png"
            )
            val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(context, "Downloading File", Toast.LENGTH_LONG).show()
        }

        return root
    }

    class MyWebViewClient(homeFragment: HomeFragment) : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {

        }


    }




}