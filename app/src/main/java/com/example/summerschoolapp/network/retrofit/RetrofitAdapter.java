package com.example.summerschoolapp.network.retrofit;

import android.text.TextUtils;

import com.example.summerschoolapp.BuildConfig;
import com.example.summerschoolapp.utils.Const;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitAdapter {

    private static Retrofit retrofitInstance = null;

    public static RetrofitAPI getRetrofitClient() {

        if (retrofitInstance == null) {

            OkHttpClient.Builder okHttpBuilder;
            okHttpBuilder = new OkHttpClient.Builder();

            okHttpBuilder.readTimeout(25, TimeUnit.SECONDS);
            okHttpBuilder.connectTimeout(25, TimeUnit.SECONDS);

             //debuging options for api calls
            if (BuildConfig.BUILD_TYPE.equals("debug")
                    || BuildConfig.BUILD_TYPE.equals("dev")
                    || BuildConfig.BUILD_TYPE.equals("previewDebug")
                    || BuildConfig.BUILD_TYPE.equals("preview")
                    || BuildConfig.BUILD_TYPE.equals("releaseDebug")) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.level(HttpLoggingInterceptor.Level.BODY);
                okHttpBuilder.addInterceptor(interceptor);
            }

            // TODO @Matko
            // use below code/logic for adding things to header in every or some requests
            // do not send token in each api call

            // add global auth header adding
            okHttpBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json");

                if (original.body() != null){
                    requestBuilder.addHeader("Content-Length", Long.toString(original.body().contentLength()));
                }

                // Adding Authorization token (API Key)
                // Requests will be denied without API key
//                if (!TextUtils.isEmpty(Preferences.getInstance(context).getCustomString(Const.Preferences.AUTH_TOKEN))) {
//                    requestBuilder.addHeader("Authorization", "JWT " + Preferences.getInstance(context).getCustomString(Const.Preferences.AUTH_TOKEN));
//                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            OkHttpClient okHttpClient = okHttpBuilder.build();

            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(Const.Api.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

            retrofitInstance = retrofitBuilder.build();
        }

        return retrofitInstance.create(RetrofitAPI.class);
    }
}
