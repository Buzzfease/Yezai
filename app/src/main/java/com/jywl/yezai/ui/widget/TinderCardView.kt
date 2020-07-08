package com.jywl.yezai.ui.widget

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jywl.yezai.R
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.utils.DisplayUtil
import com.jywl.yezai.utils.glide.GlideCenter
import timber.log.Timber

/**
 * created by Buzz
 * on 2020/7/7
 * email lmx2060918@126.com
 */


class TinderCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), OnTouchListener {
    private var iv: ImageView? = null
    private var tv_name: TextView? = null
    private var iv_tips: ImageView? = null
    private var padding = 0
    private var downX = 0f
    private var downY = 0f
    private var newX = 0f
    private var newY = 0f
    private var dX = 0f
    private var dY = 0f
    private var rightBoundary = 0f
    private var leftBoundary = 0f
    private var screenWidth = 0
    private var listener: OnLoadMoreListener? = null

    fun init(context: Context) {
        if (!isInEditMode) {
            View.inflate(context, R.layout.layout_tantan_cardview, this)
            screenWidth = DisplayUtil.getScreenWidth(context)
            leftBoundary = screenWidth * (1.0f / 6.0f) //是否左滑的边界
            rightBoundary = screenWidth * (5.0f / 6.0f) //是否右滑的边界
            iv = findViewById<View>(R.id.iv) as ImageView
            tv_name = findViewById<View>(R.id.tv_name) as TextView
            iv_tips = findViewById<View>(R.id.iv_tips) as ImageView
            padding = DisplayUtil.dip2px(context, PADDINGVALUE)
            setOnTouchListener(this)
        }
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent): Boolean {
        val x: Float = motionEvent.getX()
        val y: Float = motionEvent.getY()

        val action: Int = motionEvent.getAction()
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x
                downY = y
                Timber.e("ACTION_DOWN    " + downX)
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX: Float = Math.abs(x - downX)
                val deltaY: Float = Math.abs(y - downY)
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return true
                }
            }
        }

        return super.onInterceptTouchEvent(motionEvent)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val tinderStackLayout = view.parent as TinderStackLayout
        val topCard =
            tinderStackLayout.getChildAt(tinderStackLayout.childCount - 1) as TinderCardView
        return if (topCard == view) {
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = motionEvent.x
                    downY = motionEvent.y
                    view.clearAnimation()

                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    newX = motionEvent.x
                    newY = motionEvent.y
                    dX = newX - downX //手指移动距离
                    Timber.e("ACTION_MOVE    " + newX)
                    dY = newY - downY
                    val posX = view.getX() + dX
                    view.setX(view.getX() + dX) //view的新距离 需求1，卡片随手指的移动而移动
                    //view.setY(view.getY() + dY)//新需求，只移动x坐标


                    val rotation =
                        CARD_ROTATION_DEGREES * posX / screenWidth
                    val halfCardHeight = view.getHeight() / 2
                    if (downY < halfCardHeight - 2 * padding) {
                        view.setRotation(rotation) //设置View在Z轴上的旋转角度 需求2，卡片移动过程中，随距离的增大而，选择角度增大
                    } else {
                        view.setRotation(-rotation)
                    }
                    val alpha = (posX - padding) / (screenWidth * 0.3f)
                    if (alpha > 0) { //需求3, 判断手指的移动方向，显示选择/删除图标，同时图标随距离的增大，透明度增加
                        iv_tips!!.alpha = alpha
                        iv_tips!!.setImageResource(R.mipmap.icon_dianzan)
                    } else {
                        iv_tips!!.alpha = -alpha
                        iv_tips!!.setImageResource(R.mipmap.icon_tese)
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (isBeyondLeftBoundary(view)) {
                        removeCard(view, -(screenWidth * 2)) //移动view.向左边移出屏幕
                    } else if (isBeyondRightBoundary(view)) {
                        removeCard(view, screenWidth * 2)
                    } else {
                        resetCard(view) //复原view
                    }
                    true
                }
                else -> super.onTouchEvent(motionEvent)
            }
        } else super.onTouchEvent(motionEvent)
    }

    private fun isBeyondLeftBoundary(view: View): Boolean {
        return view.x + view.width / 2 < leftBoundary
    }

    private fun isBeyondRightBoundary(view: View): Boolean {
        return view.x + view.width / 2 > rightBoundary
    }

    private fun removeCard(view: View, xPos: Int) {
        view.animate()
            .x(xPos.toFloat()) //x轴移动距离
            .y(0f) //y轴移动距离
            .setInterpolator(AccelerateInterpolator()) //插值器   在动画开始的地方速率改变比较慢，然后开始加速
            .setDuration(DURATIONTIME.toLong()) //移动距离
            .setListener(object : Animator.AnimatorListener {
                //监听
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) { //移出后回调
                    val viewGroup = view.parent as ViewGroup
                    if (viewGroup != null) {
                        viewGroup.removeView(view)
                        val count = viewGroup.childCount //需求5，增加新卡片
                        if (count == 1 && listener != null) {
                            listener!!.onLoad()
                        }
                    }
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
    }

    private fun resetCard(view: View) {
        view.animate()
            .x(0f) //x轴移动
            .y(0f) //y轴移动
            .rotation(0f) //循环次数
            .setInterpolator(OvershootInterpolator()).duration = DURATIONTIME.toLong()
        iv_tips!!.alpha = 0f //图标隐藏
    }

    fun bind(u: UserBean?) {  //view加载数据
        //加载图片 加载名字
        GlideCenter.get().showImage(iv, u?.userAvatar)
        tv_name?.text = u?.userName

    }

    interface OnLoadMoreListener {
        fun onLoad()
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener?) {
        this.listener = listener
    }

    companion object {
        private const val PADDINGVALUE = 16f
        private const val CARD_ROTATION_DEGREES = 40.0f
        const val DURATIONTIME = 300
    }

    init {
        init(context)
    }
}