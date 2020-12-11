package com.themahi.intellicare.net

import android.util.Log
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

object ImageWorker {
    suspend fun uploadFile(file: File?, mimeType: String?) : ResponseBody? {
        // create upload service client
        val service = RetrofitClient.retrofit.create(RetrofitAPI::class.java)

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // create RequestBody instance from file "application/json"
        val imageBody = RequestBody.create(MediaType.parse(mimeType), file)

        // MultipartBody.Part is used to send also the actual file name
        val imagePart = MultipartBody.Part.createFormData("image",  /*file.getName()*/"kvk-file", imageBody)

        // add another part within the multipart request
        val typeBody = RequestBody.create(MultipartBody.FORM, "profile")

        // finally, execute the request
        return service.upload(typeBody, imagePart)

    }

    suspend fun getFile() : ResponseBody? {
        // create upload service client
        val service = RetrofitClient.retrofit.create(RetrofitAPI::class.java)

        val body: MutableMap<String, String> = HashMap()
        body["type"] = "profile"
        body["patient_id"] = "4"

        // finally, execute the request
        return service.getUserFile("some_token", body)
    }
}
