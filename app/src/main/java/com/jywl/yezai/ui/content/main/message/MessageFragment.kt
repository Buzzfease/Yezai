package com.jywl.yezai.ui.content.main.message

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment

class MessageFragment : BaseMvpFragment<MessagePresenter>(),
    MessageContract.View {

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_message
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }
}