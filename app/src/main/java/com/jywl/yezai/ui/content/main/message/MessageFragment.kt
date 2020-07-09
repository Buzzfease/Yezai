package com.jywl.yezai.ui.content.main.message

import android.os.Handler
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_message.*
import timber.log.Timber


class MessageFragment : BaseMvpFragment<MessagePresenter>(), MessageContract.View,BaseQuickAdapter.RequestLoadMoreListener {

    private var mAdapter = MessageAdapter()

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_message
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        initRecyclerView()
        setData(false)
    }

    override fun onLoadMoreRequested() {
        Handler().postDelayed({
            setData(true)
        },1000)

        Timber.e("onLoadMoreRequested")
    }

    private fun initRecyclerView(){
        //initAdapter
        mAdapter.run {
            setEnableLoadMore(true)
            setPreLoadNumber(0)
            setOnLoadMoreListener(this@MessageFragment, recyclerView)
        }

        //initRecyclerView
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    private fun setData(loadMore :Boolean){
        val list = ArrayList<MultiTypeItem<Any>>()

        val user = UserBean("https://api.xygeng.cn/Bing/",
            "Buzz")

        repeat(10){
            list.add(MultiTypeItem(MessageAdapter.ITEM_MESSAGE, user))
        }

        if (loadMore) mAdapter.addData(list) else mAdapter.replaceData(list)
        mAdapter.loadMoreComplete()

        Timber.e("setData")
    }

}