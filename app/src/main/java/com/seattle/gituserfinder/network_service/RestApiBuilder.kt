package com.seattle.gituserfinder.network_service

import android.util.Base64
import com.seattle.gituserfinder.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApiBuilder {

    companion object {
        var serviceClient: RestApiService? = null

        fun getClient(): RestApiService? {

            if (serviceClient == null) {

                val httpClient = OkHttpClient.Builder()
                httpClient.connectTimeout(15, TimeUnit.SECONDS)
                httpClient.readTimeout(15, TimeUnit.SECONDS)
                //
                // add logs interceptor
                val logging = HttpLoggingInterceptor()
                // set your desired log level
                logging.level = HttpLoggingInterceptor.Level.BODY
                // add logging as last interceptor
                httpClient.addInterceptor(logging)

                val builder = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())

                val client = httpClient.build()
                val retrofit = builder.client(client).build()
                serviceClient = retrofit.create(RestApiService::class.java)
            }
            return serviceClient
        }
    }
}