package com.themahi.intellicare.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import android.net.Uri;
import android.os.FileUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://13.235.114.67/test/api/v1/";
    private static RetrofitClient client;

    private RetrofitClient() {

    }

    public static RetrofitClient getInstance() {
        if (null == client) {
            client = new RetrofitClient();
        }
        return client;
    }

    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLoggingClient())
                .build();
        return retrofit;
    }

    public void uploadFile(File file, String mimeType) {
        // create upload service client
        RetrofitAPI service = getRetrofit().create(RetrofitAPI.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // create RequestBody instance from file "application/json"
        RequestBody imageBody = RequestBody.create(MediaType.parse(mimeType), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", /*file.getName()*/"kvk-file", imageBody);

        // add another part within the multipart request
        RequestBody typeBody = RequestBody.create(MultipartBody.FORM, "profile");

        // finally, execute the request
        Call<ResponseBody> call = service.upload(typeBody, imagePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
                try {
                    Log.e("Upload", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private OkHttpClient getLoggingClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor);
        return httpBuilder.build();
    }
}
