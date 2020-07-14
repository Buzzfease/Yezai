package com.jywl.yezai.ui.content.imagepreview

import android.app.Dialog
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.jywl.yezai.FinalParams
import com.jywl.yezai.R
import com.jywl.yezai.entity.PicWithExt
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.utils.DialogFactory
import kotlinx.android.synthetic.main.activity_image_preview.*
import timber.log.Timber

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 * https://blog.csdn.net/LosingCarryJie/article/details/79841234
 * https://blog.csdn.net/hmyang314/article/details/51415396
 */
class ImagePreviewActivity : BaseMvpActivity<ImagePreviewPresenter>(), ImagePreviewContract.View{
    private var imageList: List<PicWithExt>? = null
    private var mStartPosition: Int = 0
    private var mCurrentPosition: Int = 0
    private var adapter: ImagePreviewAdapter? = null
    private var picDialog: Dialog? = null
    private var startScrollingPosition = 0

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_image_preview
    }

    override fun initViewAndEvent() {
        getIntentData()
        renderView()
        getData()
        setListener()
    }

    private fun setListener() {
        main_linear!!.getChildAt(mStartPosition).isEnabled = true
        imageBrowseViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                Timber.e("onPageSelected : "+ position)
                hideAllIndicator(position)
                main_linear!!.getChildAt(position).isEnabled = true
                mCurrentPosition = position
                //adapter?.startPlay(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                Timber.e("onPageScrollStateChanged : "+ state)
                if (state == 1){
                    //开始滑动
                    startScrollingPosition = imageBrowseViewPager?.currentItem!!
                    //adapter?.pause(startScrollingPosition)
                }
                if (state == 0){
                    //完全滑动完毕了,或没有滑动
                    if (startScrollingPosition == imageBrowseViewPager?.currentItem!!){
                        //adapter?.resume(startScrollingPosition)
                    }
                }
            }
        })
        imageBrowseViewPager!!.setPageTransformer(false) { page, position ->
            val normalizedPosition = Math.abs(Math.abs(position) - 1)
            page.scaleX = normalizedPosition / 2 + 0.5f
            page.scaleY = normalizedPosition / 2 + 0.5f
        }
    }


    private fun hideAllIndicator(position: Int) {
        for (i in imageList!!.indices) {
            if (i != position) {
                main_linear!!.getChildAt(i).isEnabled = false
            }
        }
    }


    private fun renderView() {
        if (imageList == null) return
        if (imageList!!.size == 1) {
            main_linear!!.visibility = View.GONE
        } else {
            main_linear!!.visibility = View.VISIBLE
        }
        adapter = ImagePreviewAdapter(this, imageList, mStartPosition, object : ImagePreviewAdapter.IdialogListener{
            override fun showDialog() {
                if (picDialog == null){
                    picDialog = DialogFactory.getLoadingDialog(this@ImagePreviewActivity)
                    picDialog?.show()
                    picDialog?.setCanceledOnTouchOutside(true)
                    picDialog?.setOnCancelListener { onBackPressed() }
                }
            }

            override fun hideDialog() {
                if (picDialog!= null){
                    picDialog?.dismiss()
                }
            }
        })
        imageBrowseViewPager?.adapter = adapter
        imageBrowseViewPager?.offscreenPageLimit = imageList!!.size
        imageBrowseViewPager?.currentItem = mStartPosition
    }

    private fun getIntentData() {
        if (intent != null) {
            mStartPosition = intent.getIntExtra(FinalParams.START_IMAGE_POSITION, 0)
            mCurrentPosition = mStartPosition
            imageList = intent.getSerializableExtra(FinalParams.IMAGE_LIST) as List<PicWithExt>
        }
    }

    /**
     * 获取数据
     */
    private fun getData() {
        var view: View
        for (pic in imageList!!) {
            //创建底部指示器(小圆点)
            view = View(this@ImagePreviewActivity)
            view.setBackgroundResource(R.drawable.select_guide_indicator)
            view.isEnabled = false
            //设置宽高
            var layoutParams = LinearLayout.LayoutParams(20, 20)
            //设置间隔
            if (pic != imageList!![0]) {
                layoutParams.setMargins(100,0,0,0)
            }
            //添加到LinearLayout
            main_linear!!.addView(view, layoutParams)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        picDialog?.dismiss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_fade_out, R.anim.anim_fade_in)
    }

}
