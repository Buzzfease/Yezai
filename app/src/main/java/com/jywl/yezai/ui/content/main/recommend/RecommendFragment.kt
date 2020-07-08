package com.jywl.yezai.ui.content.main.recommend

import androidx.core.content.ContextCompat
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.widget.FlowTagView
import com.jywl.yezai.utils.DisplayUtil
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment : BaseMvpFragment<RecommendPresenter>(),
    RecommendContract.View {

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

        initUserDataTagView()
        initUserTargetTagView()
    }

    private fun initUserDataTagView(){
        val array = arrayOf("未婚","26岁","天枰座","162cm","48KG","工作地：西安雁塔区","月收入：5-8千","大学本科")
        tagViewUserData!!.datas(array)
            //下面的5个方法若不设置，则会采用默认值
            .textColor(ContextCompat.getColor(requireContext(), R.color.textColorGrey), ContextCompat.getColor(requireContext(), R.color.textColorGrey))
            .textSize(DisplayUtil.sp2px(requireContext(), 14))
            .backgroundColor(ContextCompat.getColor(requireContext(), R.color.gray), ContextCompat.getColor(requireContext(), R.color.gray))
            .itemHeight(DisplayUtil.dip2px(requireContext(),25f))
            .padding(DisplayUtil.dip2px(requireContext(), 18f), DisplayUtil.dip2px(requireContext(), 10f), DisplayUtil.dip2px(requireContext(), 8f))
            .commit()
    }

    private fun initUserTargetTagView(){
        val array = arrayOf("未婚","26-35岁","未婚","没有孩子","48KG","工作地：西安雁塔区","月收入：5-8千","大学本科")
        tagViewTargetCondition!!.datas(array)
            //下面的5个方法若不设置，则会采用默认值
            .textColor(ContextCompat.getColor(requireContext(), R.color.textColorGrey), ContextCompat.getColor(requireContext(), R.color.textColorGrey))
            .textSize(DisplayUtil.sp2px(requireContext(), 14))
            .backgroundColor(ContextCompat.getColor(requireContext(), R.color.gray), ContextCompat.getColor(requireContext(), R.color.gray))
            .itemHeight(DisplayUtil.dip2px(requireContext(),25f))
            .padding(DisplayUtil.dip2px(requireContext(), 18f), DisplayUtil.dip2px(requireContext(), 10f), DisplayUtil.dip2px(requireContext(), 8f))
            .commit()
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