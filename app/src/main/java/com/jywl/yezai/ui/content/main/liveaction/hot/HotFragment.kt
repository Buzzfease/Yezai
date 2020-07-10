package com.jywl.yezai.ui.content.main.liveaction.hot

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.LiveActionAdapter
import kotlinx.android.synthetic.main.fragment_hot.*
import timber.log.Timber

class HotFragment : BaseMvpFragment<HotPresenter>(),
    HotContract.View {

    private var mAdapter = LiveActionAdapter()

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
        initRecyclerView()
        setData(false)
    }

    private fun initRecyclerView(){
        //initAdapter
        mAdapter.run {
            setEnableLoadMore(true)
            setPreLoadNumber(0)
            setOnLoadMoreListener({
                Handler().postDelayed({
                    setData(true)
                },1000)
                Timber.e("onLoadMoreRequested")
            }, recyclerView)

            addHeaderView(addHotHeaderView())
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
        val user = UserBean("https://api.xygeng.cn/Bing/", "Buzz")
        repeat(10){
            list.add(MultiTypeItem(LiveActionAdapter.ITEM_ACTION, user))
        }

        if (loadMore) mAdapter.addData(list) else mAdapter.replaceData(list)

        mAdapter.loadMoreComplete()
        Timber.e("setData")
    }

    private fun addHotHeaderView(): View {
        val header = LayoutInflater.from(requireContext()).inflate(R.layout.layout_hot_header, null) as LinearLayout
        return header
    }
}