package com.jywl.yezai.utils

import android.content.Context
import android.view.View
import android.view.ViewParent
import android.view.WindowManager
import android.widget.FrameLayout


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

    fun sp2px(context: Context, sp: Int): Int {
        val density = context.resources.displayMetrics.scaledDensity
        return (sp * density + 0.5f).toInt()
    }

    /**
     * 将View从父控件中移除
     */
    fun removeViewFormParent(v: View?) {
        if (v == null) return
        val parent: ViewParent? = v.parent
        if (parent != null && parent is FrameLayout) {
            parent.removeView(v)
        }
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }
}