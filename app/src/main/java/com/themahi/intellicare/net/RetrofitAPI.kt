package com.themahi.intellicare.net

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface RetrofitAPI {
    @Multipart //    @Headers("Content-Type: application/json") //dont add by default header is multipart/form-data
    @POST("user/upload-image")
    suspend fun upload( //@Part annotation must supply a name or use MultipartBody.Part parameter type.
            @Part("type") type: RequestBody?,
            @Part image: MultipartBody.Part? //@Part parameters using the MultipartBody.Part must not include a part name in the annotation.
            //@Part("image") MultipartBody.Part file
    ): ResponseBody?

    @Headers("Content-Type: application/json") //"token: 10c880df15b3803a62a114a0bf7e4813"
    @POST("user/file")
    suspend fun getUserFile(@Header("token") token: String, @Body body: Map<String, String>): ResponseBody?
}