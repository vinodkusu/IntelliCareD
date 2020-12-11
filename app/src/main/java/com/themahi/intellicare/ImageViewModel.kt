package com.themahi.intellicare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themahi.intellicare.net.ImageWorker
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File

class ImageViewModel : ViewModel() {
    private lateinit var imageData: MutableLiveData<ResponseBody?>
    lateinit var imageDataLD: LiveData<ResponseBody?>

    fun getImageLiveData(): LiveData<ResponseBody?> {
        imageData = MutableLiveData<ResponseBody?>()
        imageDataLD = imageData;
        getImage()
        return imageDataLD
    }

    private fun getImage() {
        viewModelScope.launch {
            imageData.postValue(ImageWorker.getFile())
        }
    }

    fun uploadImage(file: File?, mimeType: String?) /*: LiveData<ResponseBody?>*/ {
        viewModelScope.launch {
            val responseBody = ImageWorker.uploadFile(file, mimeType)

            if (responseBody != null) {
                Log.e("response", responseBody.source().toString())
                Log.e("response", responseBody.string())
            }
        }
    }
}