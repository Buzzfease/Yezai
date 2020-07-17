package com.jywl.yezai.ui.content.heartview

import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.ui.widget.VerticalColumnarGraphView
import com.jywl.yezai.utils.DisplayUtil
import com.jywl.yezai.utils.EasyStatusBar
import com.zyyoona7.popup.EasyPopup
import com.zyyoona7.popup.XGravity
import com.zyyoona7.popup.YGravity
import kotlinx.android.synthetic.main.activity_heart_view.*
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * created by Buzz
 * on 2020/7/17
 * email lmx2060918@126.com
 */
class HeartViewActivity: BaseMvpActivity<HeartViewPresenter>(), HeartViewContract.View {

    private var matchAnalisePop: EasyPopup? = null
    private var matchResultPop: EasyPopup? = null
    private var matchHelpPop:EasyPopup? = null

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_heart_view
    }

    override fun initViewAndEvent() {
        EasyStatusBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)
        tvTitle.text = "心灵视界"
        ivBack.setOnClickListener {
            onBackPressed()
        }
        llPiPei.setOnClickListener {
            showMatchAnalisePop()
        }
        ivHeartRateHelp.setOnClickListener {
            showMatchHelpPop()
        }

        initHeartRateView()
    }

    private fun initHeartRateView(){
        verticalColumnar.setOnColumnarItemClickListener(object :VerticalColumnarGraphView.OnColumnarItemClickListener{
            override fun onColumnarItemClick(
                view: VerticalColumnarGraphView?, selectedIndex: Int,
                columnarItem: VerticalColumnarGraphView.ColumnarItem,
                userItem: VerticalColumnarGraphView.UserRate) {

                userHeartRateTitle.text = userItem.rateTitle[selectedIndex].plus(userItem.rate!![selectedIndex].toString())
                userHeartRateDesc.text = userItem.rateDesc[selectedIndex]
            }
        })

        val rateA: IntArray? = intArrayOf(55, 79, 62, 33, 16, 85)
        val rateB: IntArray? = intArrayOf(21, 7, 95, 54, 72, 16)
        val rateTitle:Array<String> = arrayOf("他的性格特点", "他的生活态度", "他的处事风格", "他的爱情观念", "他的消费观念", "他的家庭关系")
        val rateDesc:Array<String> = arrayOf("他的性格特点主要是blablabla", "他的生活态度主要是blablabla", "他的处事风格主要是blablabla", "他的爱情观念主要是blablabla", "他的消费观念主要是blablabla", "他的家庭关系主要是blablabla")
        val dataA = VerticalColumnarGraphView.UserRate()
        val dataB = VerticalColumnarGraphView.UserRate()
        dataA.rateTitle = rateTitle
        dataA.rateDesc = rateDesc
        dataA.rate = rateA
        dataB.rateTitle = rateTitle
        dataB.rateDesc = rateDesc
        dataB.rate = rateB
        verticalColumnar.setData(dataA, dataB)
    }

    /**
     * 显示大数据匹配分析弹出框
     */
    private fun showMatchAnalisePop(){
        matchAnalisePop = EasyPopup.create()
            .setContentView(this, R.layout.layout_match_analise_pop)
            .setAnimationStyle(R.style.MD_WindowAnimation)
            .setBackgroundDimEnable(true)
            .setDimValue(0.4f)
            .setWidth(DisplayUtil.dip2px(this, 300f))
            .setDimColor(Color.BLACK)
            .setFocusAndOutsideEnable(false)
            .apply()
        val tvGoResult: TextView? = matchAnalisePop?.findViewById(R.id.tvGoResult)
        tvGoResult?.setOnClickListener {
            matchAnalisePop?.dismiss()
            showMatchResultPop()
        }
        val ivClose: ImageView? = matchAnalisePop?.findViewById(R.id.ivClose)
        ivClose?.setOnClickListener { matchAnalisePop?.dismiss() }
        val llItem1: LinearLayout? = matchAnalisePop?.findViewById(R.id.llItem1)
        val llItem2: LinearLayout? = matchAnalisePop?.findViewById(R.id.llItem2)
        val llItem3: LinearLayout? = matchAnalisePop?.findViewById(R.id.llItem3)
        val ivDot1: ImageView? = matchAnalisePop?.findViewById(R.id.ivDot1)
        val ivDot2: ImageView? = matchAnalisePop?.findViewById(R.id.ivDot2)
        val ivDot3: ImageView? = matchAnalisePop?.findViewById(R.id.ivDot3)
        val ivCheck1: ImageView? = matchAnalisePop?.findViewById(R.id.ivCheck1)
        val ivCheck2: ImageView? = matchAnalisePop?.findViewById(R.id.ivCheck2)
        val ivCheck3: ImageView? = matchAnalisePop?.findViewById(R.id.ivCheck3)
        val tvHint1:TextView? = matchAnalisePop?.findViewById(R.id.tvHint1)
        val tvHint2:TextView? = matchAnalisePop?.findViewById(R.id.tvHint2)
        val tvHint3:TextView? = matchAnalisePop?.findViewById(R.id.tvHint3)

        matchAnalisePop?.showAtAnchorView(window!!.decorView, YGravity.CENTER, XGravity.CENTER)
        val selectedDrawable = ContextCompat.getDrawable(this, R.mipmap.icon_xuanzhong)

        llItem1?.background = selectedDrawable
        ivDot1?.isSelected = true
        tvHint1?.setTextColor(ContextCompat.getColor(this, R.color.lover_brown))
        Handler().postDelayed({
            ivCheck1?.visibility = View.VISIBLE
            llItem1?.background = null
            llItem2?.background = selectedDrawable
            ivDot2?.isSelected = true
            tvHint2?.setTextColor(ContextCompat.getColor(this, R.color.lover_brown))
            Handler().postDelayed({
                ivCheck2?.visibility = View.VISIBLE
                llItem2?.background = null
                llItem3?.background = selectedDrawable
                ivDot3?.isSelected = true
                tvHint3?.setTextColor(ContextCompat.getColor(this, R.color.lover_brown))
                Handler().postDelayed({
                    ivCheck3?.visibility = View.VISIBLE
                    llItem3?.background = null
                    tvGoResult?.visibility = View.VISIBLE
                }, 1500)
            }, 1500)
        }, 1500)
    }

    private fun showMatchResultPop(){
        matchResultPop = EasyPopup.create()
            .setContentView(this, R.layout.layout_match_result_pop)
            .setAnimationStyle(R.style.MD_WindowAnimation)
            .setBackgroundDimEnable(true)
            .setDimValue(0.4f)
            .setWidth(DisplayUtil.dip2px(this, 340f))
            .setDimColor(Color.BLACK)
            .setFocusAndOutsideEnable(false)
            .apply()
        val ivClose: ImageView? = matchResultPop?.findViewById(R.id.ivClose)
        ivClose?.setOnClickListener { matchResultPop?.dismiss() }

        matchResultPop?.showAtAnchorView(window!!.decorView, YGravity.CENTER, XGravity.CENTER)
    }

    private fun showMatchHelpPop() {
        matchHelpPop = EasyPopup.create()
            .setContentView(this, R.layout.layout_match_help_pop)
            .setAnimationStyle(R.style.MD_WindowAnimation)
            .setBackgroundDimEnable(true)
            .setDimValue(0.4f)
            .setWidth(DisplayUtil.dip2px(this, 340f))
            .setDimColor(Color.BLACK)
            .setFocusAndOutsideEnable(false)
            .apply()
        val ivClose: ImageView? = matchHelpPop?.findViewById(R.id.ivClose)
        ivClose?.setOnClickListener { matchHelpPop?.dismiss() }

        matchHelpPop?.showAtAnchorView(window!!.decorView, YGravity.CENTER, XGravity.CENTER)
    }
}