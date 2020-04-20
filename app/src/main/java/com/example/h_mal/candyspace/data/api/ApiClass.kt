package com.example.h_mal.candyspace.data.api

import com.example.h_mal.candyspace.data.api.model.ApiResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiClass {

    @GET("users?")
    suspend fun getUsersFromApi(@Query("inname") inname: String): Response<ApiResponse>

    //invoke method creating an invocation of the api call
    companion object{
        operator fun invoke(
            //injected @params
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            queryParamsInterceptor: QueryParamsInterceptor
        ) : ApiClass{

            //okHttpClient with interceptors
            val okkHttpclient = OkHttpClient.Builder()
                .addNetworkInterceptor(networkConnectionInterceptor)
                .addInterceptor(queryParamsInterceptor)
                .build()

            //retrofit to be used in @Repository
            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://api.stackexchange.com/2.2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClass::class.java)
        }
    }
}