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
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    //1毫秒(ms)=1000000纳秒(ns)
    private val DEFAULT_CLICK_TIME: Long = 1000000000

    private var paint: Paint? = null
    private var textPaint: TextPaint? = null

    private val topLabels = arrayOf("外向", "追求", "自主", "亲密", "随性", "融入")
    private val bottomLabels = arrayOf("内向", "知足", "通融", "独立", "计划", "独处")

    private val labelTextColor = 0xff37373                 //字体颜色
    private val labelTextColorSelected = 0xffffbc97         //选中字体颜色
    private var labelTextSize = 0f                          //坐标字体大小

    private var clipRect: Rect = Rect()
    private var rect: Rect = Rect()
    private var rectF = RectF()


    private var space = 0       //柱形间隔
    private val column = 7      //柱形数目

    private val lOffset = 60    //左侧预留
    private val tOffset = 20    //顶部预留
    private val rOffset = 20    //右侧预留
    private val bOffset = 40    //底部预留

    private var items :List<ColumnarItem>? = null  //数据

    private var lastSelectedIndex = -1      //上一次点击选中柱形
    private var selectedIndex = -1          //点击选中柱形

    private val onColumnarItemClickListener: OnColumnarItemClickListener? = null
    private var pressedTimStamp: Long = 0
    private var mPressed = false

    private fun init(){
        paint = Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG);
        labelTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, context.resources.displayMetrics)

        setItems(createTestData());
    }


    private fun createTestData(): List<ColumnarItem> {
        val data: MutableList<ColumnarItem> = ArrayList()
        val ratios = floatArrayOf(1f, 1f, 1f, 1f, 1f, 1f, 1f)
        val colors = intArrayOf(0x80ff4343.toInt(), 0x80ffbc97.toInt(), 0x80fff177.toInt(), 0x80b5fb86.toInt(), 0x80a7dcfb.toInt(), 0x80a193ff.toInt())
        val labels = arrayOf("性格特点", "生活态度", "处事风格", "爱情观念", "消费观念", "家庭关系")
        val values = arrayOf("这种性格特点", "这种生活态度", "这种处事风格", "这种爱情观念", "这种消费观念", "这种家庭关系")
        for (i in 0 until column) {
            val item = ColumnarItem()
            item.color = colors[i]
            item.ratio = ratios[i]
            item.label = labels[i]
            item.value = values[i]
            data.add(item)
        }
        return data
    }

    //设置数据并重绘
    fun setItems(items: List<ColumnarItem>) {
        this.items = items
        invalidate()
    }

    //获取选中的柱形index,
    //hExpandClickPixel 横向的额外点击区域提高点击的灵敏度
    private fun getSelectedIndex(x: Float, y: Float, hExpandClickPixel: Int): Int {
        if (items == null || items!!.isEmpty()) return -1
        for (i in items!!.indices) {
            val item: ColumnarItem = items!![i]
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
            val item = if (selectedIndex < 0) null else items!![selectedIndex]
            if (onColumnarItemClickListener != null && item != null) {
                onColumnarItemClickListener.onColumnarItemClick(this, selectedIndex, item)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        clipRect[paddingLeft, paddingTop, width - paddingRight] = height - paddingBottom
//        textPaint!!.color = labelTextColor
//        textPaint!!.textSize = labelTextSize
//        textPaint!!.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
//        textPaint!!.alpha = 0xFF

        clipRect.set(paddingLeft + lOffset, paddingTop + tOffset, width - paddingRight - rOffset, height - paddingBottom - bOffset);
        drawItems(canvas)
    }

    //画柱形
    private fun drawItems(canvas: Canvas) {
        if (items == null || items!!.isEmpty()) return
        paint!!.style = Paint.Style.FILL
        val chartWidth: Int = calculateSuitableChartWidth(clipRect.width())
        space = chartWidth
        val chartHeight = clipRect.height()
        for (i in items!!.indices) {
            val item = items!![i] ?: continue
            paint!!.color = item.color
            val ratioHeight = chartHeight * item.ratio
            item.left = clipRect.left + space * (i + 1) + (chartWidth * i).toFloat()
            item.top = clipRect.top + chartHeight - ratioHeight
            item.right = item.left + chartWidth
            item.bottom = clipRect.bottom.toFloat()
            item.initRectF(rectF)
            if (rectF.height() > 0) {
                if (i == selectedIndex) {
                    paint!!.alpha = 0xFF
                    drawSelectedItem(canvas, item, rectF, paint)
                } else {
                    paint!!.alpha = if (selectedIndex < 0) 0xFF else 0x66
                    canvas.drawRoundRect(rectF, 4f, 4f, paint!!)
                }
            }
        }
        paint!!.alpha = 0xFF
    }

    //画选中的柱形
    private fun drawSelectedItem(canvas: Canvas, item: ColumnarItem?, rectF: RectF?, paint: Paint?) {
        canvas.drawRoundRect(rectF!!, 4f, 4f, paint!!)
    }


    //计算柱形的宽度
    private fun calculateSuitableChartWidth(width: Int): Int {
        return width / (column * 2 + 1)
    }


    //柱状图点击监听，返回点击的柱状图index, 如果没有选中则返回-1
    interface OnColumnarItemClickListener {
        fun onColumnarItemClick(view: VerticalColumnarGraphView?, selectedIndex: Int, item:ColumnarItem)
    }

    data class ColumnarItem (
        var color: Int = Color.BLUE,
        var ratio:Float = 0f,     //比率, 柱形高度百分比
        var left:Float  = 0f,      //无需设置
        var top:Float = 0f,        //无需设置
        var right:Float = 0f,      //无需设置
        var bottom:Float = 0f,     //无需设置
        var label: String = "title",  //点击柱形所显示详细信息之标签
        var value: String = "desc"  //点击柱形所显示详细信息之值
    ){
        fun initRectF(rect: RectF?) {
            if (rect == null) return
            rect[left, top, right] = bottom
        }
    }
}