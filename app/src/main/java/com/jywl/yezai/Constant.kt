package com.jywl.yezai

object Constant {
    private const val URL_PRODUCTION :String = "https://www.baidu.com"
    private const val URL_TEST :String = "https://www.baidu.com"


    /**
     * 根据环境获取接口请求地址
     * envType 1生产2测试
     */
    fun getInterFaceUrl(): String {
        when (BuildConfig.ENV_TYPE) {
            1 -> return URL_PRODUCTION
            2 -> return URL_TEST
        }
        return ""
    }

    /**
     * 根据环境获取是否显示log
     * envType 1生产2测试
     */
    fun getIsShowLog(): Boolean {
        when (BuildConfig.ENV_TYPE) {
            1 -> return false
            2 -> return true
        }
        return false
    }
}