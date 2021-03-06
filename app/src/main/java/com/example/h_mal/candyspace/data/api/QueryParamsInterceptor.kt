package com.example.h_mal.candyspace.data.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class QueryParamsInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("site", "stackoverflow")
            .addQueryParameter("pagesize","20")
            .addQueryParameter("order","desc")
            .addQueryParameter("sort","reputation")
            .build()

        // Request customization: add request headers
        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}