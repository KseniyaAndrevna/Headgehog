package com.kseniyaa.headgehog

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var api: Api

    override fun onCreate() {
        super.onCreate()

        val level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = level

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.icndb.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

        api = retrofit.create(Api::class.java)
    }
}

