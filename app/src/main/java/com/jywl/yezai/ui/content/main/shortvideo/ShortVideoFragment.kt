package com.jywl.yezai.ui.content.main.shortvideo

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment

class ShortVideoFragment : BaseMvpFragment<ShortVideoPresenter>(),
    ShortVideoContract.View {

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_short_video
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }
}