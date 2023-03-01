package com.example.wheatherapplication.data.remote.interceptors

import com.example.wheatherapplication.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OpenWeatherInterceptor @Inject constructor() :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain
            .request()
            .newBuilder()
            .url(chain.request().url.newBuilder().addQueryParameter("appid",Constants.APP_API_KEY).build()).build())
    }
}

//    Interceptor {
//        val requestBuilder=it.request().newBuilder()
//        var urlBuilder=it.request().url().newBuilder()
//        urlBuilder=urlBuilder.addQueryParameter("apikey","ar")
//        it.proceed(it.request().newBuilder().url(urlBuilder.build()).build())
//    }