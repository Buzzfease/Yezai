package com.jywl.yezai.ui.content.main.liveaction.nearby

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment


class NearByFragment : BaseMvpFragment<NearbyPresenter>(),
    NearbyContract.View {

    companion object {
        val instance: NearByFragment
            get() = NearByFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_nearby
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }

}