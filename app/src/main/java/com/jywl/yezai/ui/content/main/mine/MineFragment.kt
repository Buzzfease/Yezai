package com.jywl.yezai.ui.content.main.mine

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment

class MineFragment : BaseMvpFragment<MinePresenter>(),
    MineContract.View {

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_mine
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }
}