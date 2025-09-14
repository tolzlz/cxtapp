package com.cxtapp.network

import androidx.collection.lruCache
import retrofit2.Retrofit
import retrofit2.create

class ServiceFactory(SERVICE_CACHE_SIZE: Int = 8) {



    private val SERVICE_CACHE = lruCache<ServiceSite, Service>(SERVICE_CACHE_SIZE, create = {
        // This method is called in the get() method if a value does not already exist.
        createRetrofit(it, getBasePath(it)).create<Service>()
    })


    private fun createRetrofit(wiki: ServiceSite?, baseUrl: String): Retrofit {
        val builder = OkHttpConnectionFactory.client.newBuilder()
        builder.interceptors().add(builder.interceptors().indexOfFirst { it is HttpLoggingInterceptor }, LanguageVariantHeaderInterceptor(wiki))

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(builder.build())
            .addConverterFactory(JsonUtil.json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

}