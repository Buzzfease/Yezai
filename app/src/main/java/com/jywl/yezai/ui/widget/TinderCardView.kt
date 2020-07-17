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
import androidx.core.content.ContextCompat
import com.jywl.yezai.MyApplication
import com.jywl.yezai.R
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.utils.DisplayUtil
import kotlinx.android.synthetic.main.layout_tinder_card.view.*

/**
 * created by Buzz
 * on 2020/7/7
 * email lmx2060918@126.com
 */


class TinderCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), OnTouchListener, View.OnClickListener {

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
    private var loadMoreListener: OnLoadMoreListener? = null
    private var onChildClickListener:OnChildClickListener? = null
    private var onUserRateSelectListener:OnUserRateSelectListener? = null

    private var currentUser:UserBean? = null
    private var currentIndex:Int = 0


    fun init(context: Context) {
        View.inflate(context, R.layout.layout_tinder_card, this)
        screenWidth = DisplayUtil.getScreenWidth(context)
        leftBoundary = screenWidth * (1.0f / 6.0f) //是否左滑的边界
        rightBoundary = screenWidth * (5.0f / 6.0f) //是否右滑的边界
        padding = DisplayUtil.dip2px(context, PADDINGVALUE)
        setOnTouchListener(this)
        initPicContainer()//图片展示三宫格
        initUserHeartRate()//心灵视界对比
        initUserDataTagView()
        initUserTargetTagView()
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent): Boolean {
        val x: Float = motionEvent.getX()
        val y: Float = motionEvent.getY()

        val action: Int = motionEvent.getAction()
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x
                downY = y
                //Timber.e("ACTION_DOWN    " + downX)
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
                    //Timber.e("ACTION_MOVE    " + newX)
                    dY = newY - downY
                    val posX = view.getX() + dX
                    view.setX(view.getX() + dX) //view的新距离 需求1，卡片随手指的移动而移动
                    //view.setY(view.getY() + dY)//新需求，只移动x坐标


                    //设置View在Z轴上的旋转角度 需求2，卡片移动过程中，随距离的增大而，选择角度增大
                    val rotation = CARD_ROTATION_DEGREES * posX / screenWidth
                    val halfCardHeight = view.getHeight() / 2
                    if (downY < halfCardHeight - 2 * padding) {
                        view.setRotation(rotation)
                    } else {
                        view.setRotation(-rotation)
                    }
                    //需求3, 判断手指的移动方向，显示选择/删除图标，同时图标随距离的增大，透明度增加
//                    val alpha = (posX - padding) / (screenWidth * 0.3f)
//                    if (alpha > 0) {
//                        iv_tips!!.alpha = alpha
//                        iv_tips!!.setImageResource(R.mipmap.icon_dianzan)
//                    } else {
//                        iv_tips!!.alpha = -alpha
//                        iv_tips!!.setImageResource(R.mipmap.icon_tese)
//                    }
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
                    viewGroup.removeView(view)
                    val count = viewGroup.childCount //需求5，增加新卡片
                    if (count == 1 && loadMoreListener != null) {
                        loadMoreListener!!.onLoad()
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
        //iv_tips!!.alpha = 0f //图标隐藏
    }

    fun bind(user: UserBean, index:Int) {
        //加载用户信息
        currentUser = user
        currentIndex = index
        tvMoreAction.setOnClickListener(this)
        llPiPei.setOnClickListener(this)
        ivHeartRateHelp.setOnClickListener(this)
    }

    fun bindUserRate(dataA: VerticalColumnarGraphView.UserRate, dataB: VerticalColumnarGraphView.UserRate){
        verticalColumnar.setData(dataA, dataB)
    }

    private fun initUserDataTagView(){
        val array = arrayOf("未婚","26岁","天枰座","162cm","48KG","工作地：西安雁塔区","月收入：5-8千","大学本科")
        tagViewUserData!!.datas(array)
            //下面的5个方法若不设置，则会采用默认值
            .textColor(ContextCompat.getColor(MyApplication.instance(), R.color.textColorGrey), ContextCompat.getColor(MyApplication.instance(), R.color.textColorGrey))
            .textSize(DisplayUtil.sp2px(MyApplication.instance(), 14))
            .backgroundColor(ContextCompat.getColor(MyApplication.instance(), R.color.gray), ContextCompat.getColor(MyApplication.instance(), R.color.gray))
            .itemHeight(DisplayUtil.dip2px(MyApplication.instance(),25f))
            .padding(DisplayUtil.dip2px(MyApplication.instance(), 18f), DisplayUtil.dip2px(MyApplication.instance(), 10f), DisplayUtil.dip2px(MyApplication.instance(), 8f))
            .commit()
    }

    private fun initUserTargetTagView(){
        val array = arrayOf("未婚","26-35岁","未婚","没有孩子","48KG","工作地：西安雁塔区","月收入：5-8千","大学本科")
        tagViewTargetCondition!!.datas(array)
            //下面的5个方法若不设置，则会采用默认值
            .textColor(ContextCompat.getColor(MyApplication.instance(), R.color.textColorGrey), ContextCompat.getColor(MyApplication.instance(), R.color.textColorGrey))
            .textSize(DisplayUtil.sp2px(MyApplication.instance(), 14))
            .backgroundColor(ContextCompat.getColor(MyApplication.instance(), R.color.gray), ContextCompat.getColor(MyApplication.instance(), R.color.gray))
            .itemHeight(DisplayUtil.dip2px(MyApplication.instance(),25f))
            .padding(DisplayUtil.dip2px(MyApplication.instance(), 18f), DisplayUtil.dip2px(MyApplication.instance(), 10f), DisplayUtil.dip2px(MyApplication.instance(), 8f))
            .commit()
    }

    private fun initPicContainer(){
        //三宫格
        val imageList = ArrayList<Any>()
        repeat((1..9).random()){
            imageList.add("https://api.xygeng.cn/Bing/")
        }
        picContainer.setImageData(imageList)
    }

    private fun initUserHeartRate(){
        verticalColumnar.setOnColumnarItemClickListener(object: VerticalColumnarGraphView.OnColumnarItemClickListener{
            override fun onColumnarItemClick(view: VerticalColumnarGraphView?, selectedIndex: Int, columnarItem: VerticalColumnarGraphView.ColumnarItem, userItem: VerticalColumnarGraphView.UserRate) {
                onUserRateSelectListener?.onUserRateSelect(this@TinderCardView, currentIndex, view, selectedIndex, columnarItem, userItem)
            }
        })
    }

    override fun onClick(view: View) {
        onChildClickListener?.onChildClick(currentUser, currentIndex, view)
    }

    interface OnLoadMoreListener {
        fun onLoad()
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener?) {
        this.loadMoreListener = listener
    }

    interface OnChildClickListener{
        fun onChildClick(userBean: UserBean?, position:Int, view:View)
    }

    fun setOnChildClickListener(listener: OnChildClickListener){
        this.onChildClickListener = listener
    }

    interface OnUserRateSelectListener{
        fun onUserRateSelect(cardView: TinderCardView, cardIndex:Int, view: VerticalColumnarGraphView?, selectedIndex: Int, columnarItem: VerticalColumnarGraphView.ColumnarItem, userItem: VerticalColumnarGraphView.UserRate)
    }

    fun setOnUserRateSelectListener(listener:OnUserRateSelectListener?){
        this.onUserRateSelectListener = listener
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