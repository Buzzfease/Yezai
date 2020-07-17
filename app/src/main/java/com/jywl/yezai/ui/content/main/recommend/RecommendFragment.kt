package com.jywl.yezai.ui.content.main.recommend

import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jywl.yezai.R
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.actiondetail.UserActionActivity
import com.jywl.yezai.ui.content.heartview.HeartViewActivity
import com.jywl.yezai.ui.content.search.SearchActivity
import com.jywl.yezai.ui.widget.TinderCardView
import com.jywl.yezai.ui.widget.TinderStackLayout
import com.jywl.yezai.ui.widget.VerticalColumnarGraphView
import com.jywl.yezai.utils.DisplayUtil
import com.zyyoona7.popup.EasyPopup
import com.zyyoona7.popup.XGravity
import com.zyyoona7.popup.YGravity
import kotlinx.android.synthetic.main.fragment_recommend.*
import kotlinx.android.synthetic.main.layout_tinder_card.view.*

class RecommendFragment : BaseMvpFragment<RecommendPresenter>(),
    RecommendContract.View {

    private val mList = ArrayList<UserBean>()
    private var matchAnalisePop: EasyPopup? = null
    private var matchResultPop: EasyPopup? = null
    private var matchHelpPop:EasyPopup? = null

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_recommend
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        //showLoadingDialog()
        //presenter.testApi()

        initCardStack()
        addStackCards()
        tvSearch.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initCardStack(){
        tinderStackLayout.setOnStackLoadMoreListener(object: TinderStackLayout.OnStackLoadMoreListener{
            override fun onStackLoadMore() {
                addStackCards()
            }
        })
        tinderStackLayout.setOnStackChildClickListener(object :TinderStackLayout.OnStackChildClickListener{
            override fun onStackChildClick(userBean: UserBean?, position: Int, view: View) {
                when(view.id){
                    R.id.tvMoreAction -> {
                        toast("第" + position.toString() + "张卡的查看更多被点击了")
                        val intent = Intent(activity, UserActionActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.llPiPei -> {
                        showMatchAnalisePop()
                    }
                    R.id.ivHeartRateHelp ->{
                        showMatchHelpPop()
                    }
                    R.id.llHeartRateText ->{
                        val intent = Intent(activity, HeartViewActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
        tinderStackLayout?.setOnUserHeartRateItemSelectListener(object: TinderStackLayout.OnUserHeartRateItemSelectListener{
            override fun onUserRateItemSelect(cardView: TinderCardView, cardIndex: Int,
                                              view: VerticalColumnarGraphView?, selectedIndex: Int,
                                              columnarItem: VerticalColumnarGraphView.ColumnarItem,
                                              userItem: VerticalColumnarGraphView.UserRate) {
                cardView.findViewById<TextView>(R.id.userHeartRateTitle).text = userItem.rateTitle[selectedIndex].plus(userItem.rate!![selectedIndex].toString())
                cardView.findViewById<TextView>(R.id.userHeartRateDesc).text = userItem.rateDesc[selectedIndex]
            }
        })
    }

    private fun addStackCards(){
        repeat(5){
            mList.add(UserBean("https://api.xygeng.cn/Bing/", "Buzz"))
        }

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
        tinderStackLayout.setData(mList, dataA, dataB)
    }

    /**
     * 显示大数据匹配分析弹出框
     */
    private fun showMatchAnalisePop(){
        matchAnalisePop = EasyPopup.create()
            .setContentView(requireContext(), R.layout.layout_match_analise_pop)
            .setAnimationStyle(R.style.MD_WindowAnimation)
            .setBackgroundDimEnable(true)
            .setDimValue(0.4f)
            .setWidth(DisplayUtil.dip2px(requireContext(), 300f))
            .setDimColor(Color.BLACK)
            .setFocusAndOutsideEnable(false)
            .apply()
        val tvGoResult: TextView? = matchAnalisePop?.findViewById(R.id.tvGoResult)
        tvGoResult?.setOnClickListener {
            matchAnalisePop?.dismiss()
            showMatchResultPop()
        }
        val ivClose:ImageView? = matchAnalisePop?.findViewById(R.id.ivClose)
        ivClose?.setOnClickListener { matchAnalisePop?.dismiss() }
        val llItem1: LinearLayout? = matchAnalisePop?.findViewById(R.id.llItem1)
        val llItem2: LinearLayout? = matchAnalisePop?.findViewById(R.id.llItem2)
        val llItem3: LinearLayout? = matchAnalisePop?.findViewById(R.id.llItem3)
        val ivDot1:ImageView? = matchAnalisePop?.findViewById(R.id.ivDot1)
        val ivDot2:ImageView? = matchAnalisePop?.findViewById(R.id.ivDot2)
        val ivDot3:ImageView? = matchAnalisePop?.findViewById(R.id.ivDot3)
        val ivCheck1:ImageView? = matchAnalisePop?.findViewById(R.id.ivCheck1)
        val ivCheck2:ImageView? = matchAnalisePop?.findViewById(R.id.ivCheck2)
        val ivCheck3:ImageView? = matchAnalisePop?.findViewById(R.id.ivCheck3)
        val tvHint1:TextView? = matchAnalisePop?.findViewById(R.id.tvHint1)
        val tvHint2:TextView? = matchAnalisePop?.findViewById(R.id.tvHint2)
        val tvHint3:TextView? = matchAnalisePop?.findViewById(R.id.tvHint3)

        matchAnalisePop?.showAtAnchorView(activity?.window!!.decorView, YGravity.CENTER, XGravity.CENTER)
        val selectedDrawable = ContextCompat.getDrawable(requireContext(), R.mipmap.icon_xuanzhong)

        llItem1?.background = selectedDrawable
        ivDot1?.isSelected = true
        tvHint1?.setTextColor(ContextCompat.getColor(requireContext(), R.color.lover_brown))
        Handler().postDelayed({
            ivCheck1?.visibility = View.VISIBLE
            llItem1?.background = null
            llItem2?.background = selectedDrawable
            ivDot2?.isSelected = true
            tvHint2?.setTextColor(ContextCompat.getColor(requireContext(), R.color.lover_brown))
            Handler().postDelayed({
                ivCheck2?.visibility = View.VISIBLE
                llItem2?.background = null
                llItem3?.background = selectedDrawable
                ivDot3?.isSelected = true
                tvHint3?.setTextColor(ContextCompat.getColor(requireContext(), R.color.lover_brown))
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
            .setContentView(requireContext(), R.layout.layout_match_result_pop)
            .setAnimationStyle(R.style.MD_WindowAnimation)
            .setBackgroundDimEnable(true)
            .setDimValue(0.4f)
            .setWidth(DisplayUtil.dip2px(requireContext(), 340f))
            .setDimColor(Color.BLACK)
            .setFocusAndOutsideEnable(false)
            .apply()
        val ivClose:ImageView? = matchResultPop?.findViewById(R.id.ivClose)
        ivClose?.setOnClickListener { matchResultPop?.dismiss() }

        matchResultPop?.showAtAnchorView(activity?.window!!.decorView, YGravity.CENTER, XGravity.CENTER)
    }

    private fun showMatchHelpPop() {
        matchHelpPop = EasyPopup.create()
            .setContentView(requireContext(), R.layout.layout_match_help_pop)
            .setAnimationStyle(R.style.MD_WindowAnimation)
            .setBackgroundDimEnable(true)
            .setDimValue(0.4f)
            .setWidth(DisplayUtil.dip2px(requireContext(), 340f))
            .setDimColor(Color.BLACK)
            .setFocusAndOutsideEnable(false)
            .apply()
        val ivClose:ImageView? = matchHelpPop?.findViewById(R.id.ivClose)
        ivClose?.setOnClickListener { matchHelpPop?.dismiss() }

        matchHelpPop?.showAtAnchorView(activity?.window!!.decorView, YGravity.CENTER, XGravity.CENTER)
    }



    override fun testApiSuccess() {
        hideLoadingDialog()
        toast("success")
    }

    override fun testApiFailed(message: String?) {
        hideLoadingDialog()
        toast("failed")
    }


}