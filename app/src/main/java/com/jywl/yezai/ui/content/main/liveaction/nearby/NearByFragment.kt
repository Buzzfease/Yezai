package com.jywl.yezai.ui.content.main.liveaction.nearby

import android.os.Handler
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.LiveActionAdapter
import kotlinx.android.synthetic.main.fragment_nearby.*
import timber.log.Timber


class NearByFragment : BaseMvpFragment<NearbyPresenter>(), NearbyContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private var mAdapter = LiveActionAdapter()

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
        initRecyclerView()
        setData(false)
    }

    private fun initRecyclerView(){
        //initAdapter
        mAdapter.run {
            setEnableLoadMore(true)
            setPreLoadNumber(0)
            setOnLoadMoreListener(this@NearByFragment, recyclerView)
        }

        //initRecyclerView
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    override fun onLoadMoreRequested() {
        Handler().postDelayed({
            setData(true)
        },1000)

        Timber.e("onLoadMoreRequested")
    }

    private fun setData(loadMore :Boolean){
        val list = ArrayList<MultiTypeItem<Any>>()

        val user = UserBean("https://api.xygeng.cn/Bing/",
            "Buzz")

        repeat(10){
            list.add(MultiTypeItem(LiveActionAdapter.ITEM_ACTION, user))

        }

        if (loadMore) mAdapter.addData(list) else mAdapter.replaceData(list)
        mAdapter.loadMoreComplete()
        Timber.e("setData")
    }
}