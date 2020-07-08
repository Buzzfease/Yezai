package com.jywl.yezai.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.widget.TinderCardView.OnLoadMoreListener
import com.jywl.yezai.utils.DisplayUtil

/**
 * created by Buzz
 * on 2020/7/8
 * email lmx2060918@126.com
 */


class TinderStackLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context!!, attrs, defStyleAttr), OnLoadMoreListener {
    private var params: ViewGroup.LayoutParams? = null
    private var index = 0
    private var scaleY = 0
    private var tc: TinderCardView? = null
    private var mList: List<UserBean>? = null

    fun init() {
        params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        scaleY = DisplayUtil.dip2px(context, BASESCALE_Y_VALUE.toFloat())
    }

    private fun addCard(view: TinderCardView) {
        val count = childCount
        addView(view, 0, params)
//        val scaleX = 1 - count / BASESCALE_X_VALUE
//        view.animate()
//            .x(0f)
//            .y(count * scaleY.toFloat()) //需求6，实现层次感
//            .scaleX(scaleX) //水平缩放比例
//            .setInterpolator(AnticipateOvershootInterpolator()).duration = DURATIONTIME.toLong()
    }

    fun setDatas(list: List<UserBean>?) {
        mList = list
        if (mList == null) {
            return
        }
        val i = index
        while (index < i + STACK_SIZE) {
            tc = TinderCardView(context)
            tc!!.bind(mList!![index])
            tc!!.setOnLoadMoreListener(this)
            addCard(tc!!)
            index++
        }
    }

    override fun onLoad() {
        run {
            val i = index
            while (index < i + (STACK_SIZE - 1)) {
                if (index == mList!!.size) {
                    return
                }
                tc = TinderCardView(context)
                tc!!.bind(mList!![index])
                tc!!.setOnLoadMoreListener(this)
                addCard(tc!!)
                index++
            }
        }
//        val childCount = childCount
//        for (i in childCount - 1 downTo 0) {
//            val tinderCardView = getChildAt(i) as TinderCardView
//            val scaleValue = 1 - (childCount - 1 - i) / 50.0f
//            tinderCardView.animate()
//                .x(0f)
//                .y((childCount - 1 - i) * scaleY.toFloat())
//                .scaleX(scaleValue)
//                .rotation(0f)
//                .setInterpolator(AnticipateOvershootInterpolator()).duration =
//                DURATIONTIME.toLong()
//        }
    }

    companion object {
        const val BASESCALE_X_VALUE = 50.0f
        const val BASESCALE_Y_VALUE = 8
        const val DURATIONTIME = 300
        private const val STACK_SIZE = 5
    }

    init {
        init()
    }
}