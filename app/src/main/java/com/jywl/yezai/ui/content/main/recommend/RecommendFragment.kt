package com.jywl.yezai.ui.content.main.recommend

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment

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
        showLoadingDialog()
        presenter.testApi()
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