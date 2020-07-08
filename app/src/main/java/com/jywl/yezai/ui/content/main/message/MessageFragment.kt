package com.jywl.yezai.ui.content.main.message

import com.jywl.yezai.R
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_message.*


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
        init()
    }

    private fun init() {
        val mList = ArrayList<UserBean>()
        repeat(5){
            mList.add(UserBean("https://api.xygeng.cn/Bing/", "Buzz"))
        }
        tinderStackLayout.setDatas(mList)
    }
}