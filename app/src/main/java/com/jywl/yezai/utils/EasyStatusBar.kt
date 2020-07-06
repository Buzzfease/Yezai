package com.jywl.yezai.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat


/**
 * Created by Buzz on 2020/1/9.
 * Email :lmx2060918@126.com
 */
object EasyStatusBar {

    fun makeStatusBarTransparent(activity: AppCompatActivity, isLightBg: Boolean, container: View?, vararg content: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isLightBg) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            activity.window.statusBarColor = Color.TRANSPARENT
            //适配刘海
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val lp = activity.window.attributes
                lp.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                activity.window.attributes = lp
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(container!!) { view, windowInsetsCompat ->
            for (value in content) {
                setMarginTop(value, windowInsetsCompat.systemWindowInsetTop)
            }
            windowInsetsCompat.consumeSystemWindowInsets()
        }
    }

    fun setStatusBarColor(activity: AppCompatActivity, @ColorInt barColor: Int, isLightBg: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                if (isLightBg) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
            activity.window.statusBarColor = barColor
        }
    }

    private fun setMarginTop(view: View, marginTop: Int) {
        val menuLayoutParams = view.layoutParams as MarginLayoutParams
        menuLayoutParams.setMargins(0, marginTop, 0, 0)
        view.layoutParams = menuLayoutParams
    }

}