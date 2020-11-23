package com.themahi.intellicare.net;



import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitAPI {
    @Multipart
//    @Headers("Content-Type: application/json") //dont add by default header is multipart/form-data
    @POST("user/upload-image")
    Call<ResponseBody> upload(
            //@Part annotation must supply a name or use MultipartBody.Part parameter type.
            @Part("type") RequestBody type,
            @Part MultipartBody.Part image

            //@Part parameters using the MultipartBody.Part must not include a part name in the annotation.
            //@Part("image") MultipartBody.Part file
    );
}
