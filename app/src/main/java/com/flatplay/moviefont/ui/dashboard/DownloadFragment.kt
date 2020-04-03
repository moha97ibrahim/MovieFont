package com.flatplay.moviefont.ui.dashboard

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.flatplay.moviefont.R


import java.io.File


class DownloadFragment : Fragment() {

    private lateinit var downloadViewModel: DownloadViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        downloadViewModel =
            ViewModelProviders.of(this).get(DownloadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_download, container, false)
        val textView: TextView = root.findViewById(R.id.text_download)
        downloadViewModel.text.observe(this, Observer {
            textView.text = it
        })
        
        return root
    }


}