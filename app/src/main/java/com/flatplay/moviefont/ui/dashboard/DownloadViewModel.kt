package com.flatplay.moviefont.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DownloadViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is download Fragment"
    }
    val text: LiveData<String> = _text
}