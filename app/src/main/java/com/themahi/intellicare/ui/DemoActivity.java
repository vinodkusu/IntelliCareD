package com.themahi.intellicare.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.themahi.intellicare.BaseActivity;
import com.themahi.intellicare.R;
import com.themahi.intellicare.Utils;
import com.themahi.intellicare.databinding.ActivityDemoBinding;
import com.themahi.intellicare.net.RetrofitClient;

import java.io.File;

public class DemoActivity extends BaseActivity implements View.OnClickListener {
    private final String[] mimeTypes = {"image/png", "image/jpg", "image/jpeg"};
    private ActivityDemoBinding binding;
    private File file;
    private String mimeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.image.setOnClickListener(this);
        binding.uploadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.image) {
            showPickerDialog("Choose Image");
        } else if (v.getId() == R.id.uploadImage) {
            Utils.toast("Image uploaded to server successfully", DemoActivity.this);
            uploadFile();
        }
    }

    private void uploadFile() {
        RetrofitClient.getInstance().uploadFile(file, mimeType);
    }

    @Override
    protected void onPositiveClick() { //camera
        ImagePicker.Companion.with(DemoActivity.this)
                .galleryMimeTypes(mimeTypes)
                .cameraOnly()
                .crop()
                .compress(512)
                .maxResultSize(720, 720)
                .start();
    }

    @Override
    protected void onNegativeClick() { //gallery
        ImagePicker.Companion.with(DemoActivity.this)
                .galleryMimeTypes(mimeTypes)
                .galleryOnly()
                .crop()
                .compress(512)
                .maxResultSize(720, 720)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && intent != null) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = intent.getData();
            binding.image.setImageURI(fileUri);
            mimeType = getMimeType(fileUri.toString());
            Utils.toast("mimeType: " + mimeType, DemoActivity.this);

            //You can get File object from intent
            file = ImagePicker.Companion.getFile(intent);
            //You can also get File Path from intent
            String filePath = ImagePicker.Companion.getFilePath(intent);

            binding.imagePaths.setText("fileUri: " + fileUri + "\n" +
                    "file: " + file.getAbsolutePath() + "\n" +
                    "filePath: " + filePath + "\n");

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Utils.toast(ImagePicker.Companion.getError(intent), DemoActivity.this);
        } else {
            Utils.toast("Task Cancelled", DemoActivity.this);
        }
    }

    private String getMimeType(String url) {
        if (url.lastIndexOf(".") != -1) {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getMimeTypeFromExtension(ext);
        } else {
            return null;
        }
    }

}