package com.cxtapp.network

import androidx.collection.lruCache

class ServiceFactory(SERVICE_CACHE_SIZE: Int = 8) {



    private val SERVICE_CACHE = lruCache<ServiceSite, Service>(SERVICE_CACHE_SIZE, create = {
        // This method is called in the get() method if a value does not already exist.
        createRetrofit(it, getBasePath(it)).create<Service>()
    })

}