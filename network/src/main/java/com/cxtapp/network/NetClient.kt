package com.cxtapp.network

interface NetClient {

    //<editor-fold desc="同步请求">
    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */

    fun get(path: String, tag: Any? = null, block: (IRequest.() -> Unit)? = null): IResponse
}