package com.jywl.yezai.utils.glide

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.MainThread
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jywl.yezai.BuildConfig
import com.jywl.yezai.Constant
import com.jywl.yezai.MyApplication
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Buzz on 2020/1/8.
 * Email :lmx2060918@126.com
 */
class GlideCenter {
    companion object {
        private var mInstance: GlideCenter? = null
        @MainThread
        fun get(): GlideCenter {
            if (mInstance == null) {
                mInstance = GlideCenter()
            }
            return mInstance!!
        }
    }

    init {
        MyApplication.appComponent?.inject(this)
    }

    @Inject
    @field:Named("default")
    lateinit var defaultBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCircle")
    lateinit var circleBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCrossFade")
    lateinit var crossFadeBuilder: RequestBuilder<Drawable>

    @Inject
    @field:Named("withCircleCrossFade")
    lateinit var circleCrossFadeBuilder: RequestBuilder<Drawable>


    @Inject
    @field:Named("withBitmap")
    lateinit var bitMapBuilder: RequestBuilder<Bitmap>

    fun showImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            defaultBuilder.load(url).into(imageView)
        }
    }

    fun showCircleImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            circleBuilder.load(url).into(imageView)
        }
    }

    fun showCrossFadeImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            crossFadeBuilder.load(url).into(imageView)
        }
    }

    fun showCircleCrossFadeImage(imageView: ImageView?, url:Any?){
        if (imageView == null || url == null) return
        if (handleUrls(url) != null){
            circleCrossFadeBuilder.load(url).into(imageView)
        }
    }

    fun showLongImage(context: Context, subSamplingScaleImageView: SubsamplingScaleImageView?, url:Any?, loadingProgress: ILoadingProgress?){
        if (subSamplingScaleImageView == null || url == null) return
        if (handleUrls(url) != null){
            //展示进度条
            loadingProgress?.onStarted()
            bitMapBuilder.load(url)
                .into(object : CustomViewTarget<SubsamplingScaleImageView, Bitmap>(subSamplingScaleImageView){
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        //取消进度条
                        loadingProgress?.onFinish()
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        //取消进度条
                        loadingProgress?.onFinish()
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val initImageScale = getInitImageScale(context as Activity, resource)
                        subSamplingScaleImageView.setImage(ImageSource.bitmap(resource), ImageViewState(initImageScale, PointF(0f, 0f), 0))
                        //取消进度条
                        loadingProgress?.onFinish()
                    }
                })
        }
    }

    private fun handleUrls(url:Any):Any?{
        Timber.e("Load Image  " + url)
        if (url is String || url is Int || url is File){
            if (url is String) {
                if (url.endsWith("null")) {
                    return null
                }
                if (!url.startsWith("http")) {
                    return Constant.getInterFaceUrl() + url
                }
            }
        }
        return url
    }


    //获取大图需要的宽高
    fun getInitImageScale(activity: Activity, bitmap: Bitmap): Float {
        val wm = activity.windowManager
        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height
        // 拿到图片的宽和高
        val dw = bitmap.width
        val dh = bitmap.height
        var scale = 1.0f
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw
        }
        return scale
    }

    interface ILoadingProgress{
        fun onFinish()
        fun onStarted()
    }
}