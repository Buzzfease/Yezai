package com.jywl.yezai.utils

import android.content.Context

/**
 * created by Buzz
 * on 2020/7/4
 * email lmx2060918@126.com
 */
object DisplayUtil {
    /**
     * 根据手机的分辨率从 dp的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}