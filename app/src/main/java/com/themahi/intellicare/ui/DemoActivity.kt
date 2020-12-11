package com.themahi.intellicare.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getError
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getFile
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getFilePath
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import com.themahi.intellicare.model.ImageViewModel
import com.themahi.intellicare.R
import com.themahi.intellicare.utils.Utils
import com.themahi.intellicare.databinding.ActivityDemoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File
import java.io.InputStream


class DemoActivity : BaseActivity(), View.OnClickListener {
    private val mimeTypes = arrayOf("image/png", "image/jpg", "image/jpeg")
    private var binding: ActivityDemoBinding? = null
    private var file: File? = null
    private var mimeType: String? = null
    private lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.image.setOnClickListener(this)
        binding!!.uploadImage.setOnClickListener(this)
        binding!!.getImage.setOnClickListener(this)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(ImageViewModel::class.java)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.image) {
            showPickerDialog("Choose Image")
        } else if (v.id == R.id.uploadImage) {
            Utils.toast("Image uploaded to server successfully", this@DemoActivity)

            CoroutineScope(Dispatchers.IO).launch {
                uploadFile()
            }
        } else if (v.id == R.id.getImage) {
            Utils.toast("Image downloading", this@DemoActivity)
            viewModel.getImageLiveData().observe(this@DemoActivity, Observer<ResponseBody?>() {
                val inputStream: InputStream = it.byteStream()
                val bm = BitmapFactory.decodeStream(inputStream)
                binding!!.image.setImageBitmap(bm)
            })
        }
    }

    private fun uploadFile() {
        viewModel.uploadImage(file,mimeType)
    }

    override fun onPositiveClick() { //camera
        with(this@DemoActivity)
                .galleryMimeTypes(mimeTypes)
                .cameraOnly()
                .crop()
                .compress(512)
                .maxResultSize(720, 720)
                .start()
    }

    override fun onNegativeClick() { //gallery
        with(this@DemoActivity)
                .galleryMimeTypes(mimeTypes)
                .galleryOnly()
                .crop()
                .compress(512)
                .maxResultSize(720, 720)
                .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && intent != null) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = intent.data
            binding!!.image.setImageURI(fileUri)
            mimeType = getMimeType(fileUri.toString())
            Utils.toast("mimeType: $mimeType", this@DemoActivity)

            //You can get File object from intent
            file = getFile(intent)
            //You can also get File Path from intent
            val filePath = getFilePath(intent)
            binding!!.imagePaths.text = ("""
                    fileUri: """ + fileUri + """
                    file: """ + file!!.absolutePath + """
                    filePath: """ + filePath + """
                    
                    """).trimIndent()
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Utils.toast(getError(intent), this@DemoActivity)
        } else {
            Utils.toast("Task Cancelled", this@DemoActivity)
        }
    }

    private fun getMimeType(url: String): String? {
        return if (url.lastIndexOf(".") != -1) {
            val ext = url.substring(url.lastIndexOf(".") + 1)
            val mime = MimeTypeMap.getSingleton()
            mime.getMimeTypeFromExtension(ext)
        } else {
            null
        }
    }
}
