package com.jywl.yezai.ui.content.main.liveaction.hot

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.concern.ConcernFragment

class HotFragment : BaseMvpFragment<HotPresenter>(),
    HotContract.View {

    companion object {
        val instance: HotFragment
            get() = HotFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_hot
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }

}