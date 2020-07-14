package com.jywl.yezai.ui.content.imagepreview

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.jywl.yezai.entity.PicWithExt
import com.jywl.yezai.utils.glide.GlideCenter
import com.luck.picture.lib.photoview.PhotoView
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import timber.log.Timber


class ImagePreviewAdapter(private val context: Context, private val imageList: List<PicWithExt>?, private val itemPosition: Int, dialogListener: IdialogListener?) : PagerAdapter() {
    private var mViewMap = HashMap<Int,View>()
    var dialogListener: IdialogListener? = null

    init {
        this.dialogListener = dialogListener
    }

    override fun getCount(): Int {
        return imageList?.size ?: 0
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Timber.e("instantiateItem :" +position)
        return when {
            imageList!![position].attachExt == "longImg" -> loadLongImage(container, position)
            //imageList[position].attachPath == "gif" -> loadGif(container, position)
            else -> loadImage(container, position)
        }
    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        Timber.e("destroyItem :" + position)
        container.removeView(obj as View)
    }

    //加载长图
    private fun loadLongImage(container: ViewGroup, position: Int): SubsamplingScaleImageView {
        val image = SubsamplingScaleImageView(context)
        image.isEnabled = true
        image.maxScale = 2.0f
        image.minScale = 0.8f
        GlideCenter.get().showLongImage(context, image, imageList!![position].attachPath, object :GlideCenter.ILoadingProgress {
            override fun onFinish() {
                dialogListener?.hideDialog()
            }

            override fun onStarted() {
                dialogListener?.showDialog()
            }
        })
        image.setOnClickListener { _ ->
            image.isEnabled = false
            (context as Activity).onBackPressed()
        }
        container.addView(image)
        mViewMap[position] = image
        return image
    }

    //加载普通图片
    private fun loadImage(container: ViewGroup, position: Int): PhotoView {
        val image = PhotoView(context)
        image.isEnabled = true
        image.scaleType = ImageView.ScaleType.FIT_CENTER
        image.maximumScale = 2.0f
        image.minimumScale = 0.8f
        GlideCenter.get().showImage(image, imageList!![position].attachPath)
        image.setOnClickListener { _ ->
            image.isEnabled = false
            (context as Activity).onBackPressed()
        }
        container.addView(image)
        mViewMap[position] = image
        return image
    }

//    //加载Gif
//    private fun loadGif(container: ViewGroup, position: Int): PhotoView {
//        val image = PhotoView(context)
//        image.isEnabled = true
//        image.scaleType = ImageView.ScaleType.FIT_CENTER
//        image.maximumScale = 2.0f
//        image.minimumScale = 0.8f
//        //网络图片走这里
//        BitmapUtil.loadGif(imageList!![position].attachPath, image, object : BitmapUtil.ILoadingProgress {
//            override fun onStarted() {
//                dialogListener?.showDialog()
//            }
//
//            override fun onFinish() {
//                dialogListener?.hideDialog()
//            }
//        })
//        image.setOnClickListener { _ ->
//            image.isEnabled = false
//            (context as Activity).onBackPressed()
//        }
//        container.addView(image)
//        mViewMap[position] = image
//        return image
//    }


    interface IdialogListener {
        fun showDialog()
        fun hideDialog()
    }

}