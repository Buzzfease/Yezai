package com.jywl.yezai.ui.content.main.liveaction.lover

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.nearby.NearByFragment

class LoverFragment : BaseMvpFragment<LoverPresenter>(),
    LoverContract.View {

    companion object {
        val instance: LoverFragment
            get() = LoverFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_lover
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }

}