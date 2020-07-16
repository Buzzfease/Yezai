package com.jywl.yezai.ui.widget
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


/**
 * created by Buzz
 * on 2020/7/15
 * email lmx2060918@126.com
 */
class VerticalColumnarGraphView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    //1毫秒(ms)=1000000纳秒(ns)
    private val DEFAULT_CLICK_TIME: Long = 1000000000

    private var paint: Paint? = null                //柱形画笔
    private var textPaint: TextPaint? = null        //文字画笔
    private var paintA:Paint? = null                //我的曲线画笔
    private var paintB:Paint? = null                //对方曲线画笔
    private var pathA:Path? = null                  //我的曲线路径
    private var pathB:Path? = null                  //对方曲线路径
    private var dotPaint:Paint? = null              //白点画笔

    private val labelTextColor = 0x88bc97                   //字体颜色
    private var labelTextSize = 0f                          //坐标字体大小

    private var clipRect: Rect = Rect()
    private var rectF = RectF()


    private var space = 0       //柱形间隔
    private val column = 6      //柱形数目

    private val lOffset = 5    //左侧预留
    private val tOffset = 60    //顶部预留
    private val rOffset = 5    //右侧预留
    private val bOffset = 60    //底部预留

    private var columnarData :List<ColumnarItem>? = null  //柱形数据
    private var userDataA:UserRate? = null //用户数据A
    private var userDataB:UserRate? = null //用户数据B
    private var mValuePointListA:ArrayList<PointF> = ArrayList() //用户A数据点集合
    private var mValuePointListB:ArrayList<PointF> = ArrayList() //用户B数据点集合
    private var mControlPointListA:ArrayList<PointF> = ArrayList() //用户A贝塞尔曲线控制点集合
    private var mControlPointListB:ArrayList<PointF> = ArrayList() //用户A贝塞尔曲线控制点集合
    private val SMOOTHNESS = 0.33f //贝塞尔曲线平滑度

    private var lastSelectedIndex = -1      //上一次点击选中柱形
    private var selectedIndex = -1          //点击选中柱形

    private val onColumnarItemClickListener: OnColumnarItemClickListener? = null
    private var pressedTimStamp: Long = 0

    private fun init(){
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint?.color = labelTextColor
        labelTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, context.resources.displayMetrics)
        textPaint?.textSize = labelTextSize
        textPaint?.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        textPaint?.alpha = 0xFF
        textPaint?.textAlign = Paint.Align.CENTER;

        paintA = Paint(Paint.ANTI_ALIAS_FLAG)
        paintA?.strokeWidth = 8f
        paintA?.style = Paint.Style.STROKE
        paintA?.color = Color.parseColor("#FF0844")

        paintB = Paint(Paint.ANTI_ALIAS_FLAG)
        paintB?.strokeWidth = 8f
        paintB?.style = Paint.Style.STROKE
        paintB?.color = Color.parseColor("#FFC29F")

        dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        dotPaint?.style = Paint.Style.FILL
        //dotPaint.alpha = 255
        dotPaint?.color = Color.WHITE

        pathA = Path()
        pathB = Path()

        initData()

        val dataA = UserRate()
        val dataB = UserRate()
        dataA.rate = intArrayOf(55, 79, 62, 33, 16, 85)
        dataB.rate = intArrayOf(16, 88, 56, 90, 13, 99)
        setData(dataA, dataB)
    }


    private fun initData() {
        val data: MutableList<ColumnarItem> = ArrayList()
        val ratios = floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f)
        val colors = intArrayOf(0x66ff4343, 0x66ffbc97, 0x66fff177, 0x66b5fb86, 0x66a7dcfb, 0x66a193ff)
        val topLabel = arrayOf("外向", "追求", "自主", "亲密", "随性", "融入")
        val bottomLabel = arrayOf("内向", "知足", "通融", "独立", "计划", "独处")
        for (i in 0 until column) {
            val item = ColumnarItem()
            item.color = colors[i]
            item.ratio = ratios[i]
            item.topLabel = topLabel[i]
            item.bottomLabel = bottomLabel[i]
            data.add(item)
        }
        this.columnarData = data
        invalidate()
    }

    //设置用户数据并重绘
    fun setData(dataA: UserRate, dataB: UserRate) {
        this.userDataA = dataA
        this.userDataB = dataB
        invalidate()
    }

    //获取选中的柱形index,
    //hExpandClickPixel 横向的额外点击区域提高点击的灵敏度
    private fun getSelectedIndex(x: Float, y: Float, hExpandClickPixel: Int): Int {
        if (columnarData == null || columnarData!!.isEmpty()) return -1
        for (i in columnarData!!.indices) {
            val item: ColumnarItem = columnarData!![i]
            item.initRectF(rectF)
            var top: Float = item.top
            //为了增加柱状的点击灵明度，给定柱状的最小点击区域高度为space
            if (rectF.height() > 0 && rectF.height() < space) top = item.bottom - space
            if (x >= item.left - hExpandClickPixel && x <= item.right + hExpandClickPixel && y >= top && y <= item.bottom
            ) return i
        }
        return -1
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> if (!isPressed) {
                isPressed = true
                selectedIndex = getSelectedIndex(event.x, event.y, space / 3)
                pressedTimStamp = System.nanoTime()
            }
            MotionEvent.ACTION_UP -> {
                isPressed = false
                if (System.nanoTime() - pressedTimStamp <= DEFAULT_CLICK_TIME) {
                    pressedTimStamp = 0
                    performColumnarItemClick(selectedIndex)
                }
            }
        }
        return true
    }

    private fun performColumnarItemClick(selectedIndex: Int) {
        if (lastSelectedIndex != selectedIndex) {
            lastSelectedIndex = selectedIndex
            invalidate()
            val item = if (selectedIndex < 0) null else columnarData!![selectedIndex]
            if (onColumnarItemClickListener != null && item != null) {
                onColumnarItemClickListener.onColumnarItemClick(this, selectedIndex, item)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        clipRect.set(paddingLeft + lOffset, paddingTop + tOffset, width - paddingRight - rOffset, height - paddingBottom - bOffset);
        drawColumnarAndText(canvas)

        if (userDataA != null){
            calculateRatePoint(userDataA!!, mValuePointListA)
            calculateControlPoint(mValuePointListA, mControlPointListA)
            drawUserRateLinesAndDot(mValuePointListA, mControlPointListA, pathA!!, paintA!!, canvas)
        }
        if (userDataB != null){
            calculateRatePoint(userDataB!!, mValuePointListB)
            calculateControlPoint(mValuePointListB, mControlPointListB)
            drawUserRateLinesAndDot(mValuePointListB, mControlPointListB, pathB!!, paintB!!, canvas)
        }
    }

    //画柱形与文字
    private fun drawColumnarAndText(canvas: Canvas) {
        if (columnarData.isNullOrEmpty()) return
        paint!!.style = Paint.Style.FILL
        val chartWidth: Int = calculateSuitableChartWidth(clipRect.width())
        space = chartWidth
        val chartHeight = clipRect.height()
        for (i in columnarData!!.indices) {
            val item = columnarData!![i]
            paint!!.color = item.color
            val ratioHeight = chartHeight * item.ratio
            item.left = clipRect.left + space * (i + 1) + (chartWidth * i).toFloat()
            item.top = clipRect.top + chartHeight - ratioHeight
            item.right = item.left + chartWidth
            item.bottom = clipRect.bottom.toFloat()
            item.initRectF(rectF)
            if (rectF.height() > 0) {
                if (i == selectedIndex) {
                    //设置选中的柱形，文字的透明度
                    paint!!.alpha = 0xFF
                    textPaint!!.alpha = 0xFF
                } else {
                    //设置普通的柱形，文字的透明度
                    paint!!.alpha = 0x88
                    textPaint!!.alpha = 0x88
                }
                val fontMetrics = textPaint!!.fontMetrics
                //画柱形
                canvas.drawRoundRect(rectF, 4f, 4f, paint!!)
                //画柱形顶部文字
                canvas.drawText(item.topLabel, item.left + chartWidth / 2, item.top - fontMetrics.bottom,  textPaint!!)
                //画柱底部文字
                canvas.drawText(item.bottomLabel, item.left + chartWidth / 2, item.bottom + labelTextSize, textPaint!!)
            }
        }
        paint!!.alpha = 0xFF
    }

    private fun drawUserRateLinesAndDot(valuePointList: List<PointF>, controlPointList: ArrayList<PointF>, mPath:Path, mPaint:Paint, canvas: Canvas){
        mPath.reset()
        val firstPoint = valuePointList.first()
        mPath.moveTo(firstPoint.x, firstPoint.y)
        //填充贝塞尔曲线控制点
        for (i in 0 until (valuePointList.size - 1) * 2 step 2){
            val leftControlPoint = controlPointList[i]
            val rightControlPoint = controlPointList[i + 1]
            val rightPoint = valuePointList[i / 2 + 1]
            mPath.cubicTo(leftControlPoint.x, leftControlPoint.y, rightControlPoint.x, rightControlPoint.y, rightPoint.x, rightPoint.y)
        }
        canvas.drawPath(mPath, mPaint)

        //画数值点
        valuePointList.forEachIndexed { index, pointF ->
            if (index == selectedIndex){
                canvas.drawCircle(pointF.x, pointF.y, 15f, dotPaint!!)
            }
        }
    }

    //计算柱形的宽度
    private fun calculateSuitableChartWidth(width: Int): Int {
        return width / (column * 2 + 1)
    }


    //柱状图点击监听，返回点击的柱状图index, 如果没有选中则返回-1
    interface OnColumnarItemClickListener {
        fun onColumnarItemClick(view: VerticalColumnarGraphView?, selectedIndex: Int, item:ColumnarItem)
    }

    private fun calculateRatePoint(userData:UserRate, valuePointList:ArrayList<PointF>) {
        valuePointList.clear()
        userData.rate?.forEachIndexed { index, rate ->
            val x = (columnarData!![index].left + columnarData!![index].right) / 2f
            val y = columnarData!![index].bottom - (columnarData!![index].bottom - columnarData!![index].top) * (rate / 100f)
            valuePointList.add(PointF(x, y))
        }
    }

    private fun calculateControlPoint(valuePointList: List<PointF>, controlPointList:ArrayList<PointF>) {
        controlPointList.clear()
        if (valuePointList.size <= 1) {
            return
        }
        valuePointList.forEachIndexed { index, pointF ->
            when (index) {
                0 -> {//第一项
                    //添加后控制点
                    val nextPoint = valuePointList[index + 1]
                    val controlX = pointF.x + (nextPoint.x - pointF.x) * SMOOTHNESS
                    val controlY = pointF.y
                    controlPointList.add(PointF(controlX, controlY))
                }
                valuePointList.size - 1 -> {//最后一项
                    //添加前控制点
                    val lastPoint = valuePointList[index - 1]
                    val controlX = pointF.x - (pointF.x - lastPoint.x) * SMOOTHNESS
                    val controlY = pointF.y
                    controlPointList.add(PointF(controlX, controlY))
                }
                else -> {//中间项
                    val lastPoint = valuePointList[index - 1]
                    val nextPoint = valuePointList[index + 1]
                    val k = (nextPoint.y - lastPoint.y) / (nextPoint.x - lastPoint.x)
                    val b = pointF.y - k * pointF.x
                    //添加前控制点
                    val lastControlX = pointF.x - (pointF.x - lastPoint.x) * SMOOTHNESS
                    val lastControlY = k * lastControlX + b
                    controlPointList.add(PointF(lastControlX, lastControlY))
                    //添加后控制点
                    val nextControlX = pointF.x + (nextPoint.x - pointF.x) * SMOOTHNESS
                    val nextControlY = k * nextControlX + b
                    controlPointList.add(PointF(nextControlX, nextControlY))
                }
            }
        }
    }

    data class ColumnarItem (
        var color: Int = Color.BLUE,
        var ratio:Float = 0f,     //比率, 柱形高度百分比
        var left:Float  = 0f,      //无需设置
        var top:Float = 0f,        //无需设置
        var right:Float = 0f,      //无需设置
        var bottom:Float = 0f,     //无需设置
        var topLabel: String = "top",  //柱形顶部文字
        var bottomLabel: String = "bottom"  //柱形底部文字
    ){
        fun initRectF(rect: RectF?) {
            if (rect == null) return
            rect[left, top, right] = bottom
        }
    }

    data class UserRate(
        var rate: IntArray? = intArrayOf(55, 79, 62, 33, 16, 85) ,
        var rateTitle:Array<String> = arrayOf("他的性格特点", "他的生活态度", "他的处事风格", "他的爱情观念", "他的消费观念", "他的家庭关系"),
        var rateDesc:Array<String> = arrayOf("他的性格特点主要是blablabla", "他的生活态度主要是blablabla", "他的处事风格主要是blablabla", "他的爱情观念主要是blablabla", "他的消费观念主要是blablabla", "他的家庭关系主要是blablabla")
    )
}