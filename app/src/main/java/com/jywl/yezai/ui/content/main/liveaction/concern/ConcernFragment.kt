package com.jywl.yezai.ui.content.main.liveaction.concern

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jywl.yezai.R
import com.jywl.yezai.entity.ConcernBean
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.LiveActionAdapter
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_concern.*
import timber.log.Timber

class ConcernFragment : BaseMvpFragment<ConcernPresenter>(),
    ConcernContract.View {

    private var gAdapter = ConcernGridAdapter()
    private val gAdapterList = ArrayList<MultiTypeItem<Any>>()
    private val gDataList = ArrayList<ConcernBean>()

    private var mAdapter = LiveActionAdapter()

    private var headerAdapter = ConcernHeaderAdapter()

    companion object {
        val instance: ConcernFragment
            get() = ConcernFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_concern
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        initNoConcernRecyclerView()
        setNoConcernData()

        tvConcern.setOnClickListener {
            val list = gDataList.filter { it.isConcern }
            val array = ArrayList<Int>()
            list.forEach {
                array.add(it.id)
            }
            toast(array.toString() + "被关注了")

            initRecyclerView()
            setData(false)
            llNoConcern.visibility = View.GONE
            llHasConcern.visibility = View.VISIBLE
        }
    }

    private fun initNoConcernRecyclerView(){
        //initAdapter
        gAdapter.run {
            setEnableLoadMore(false)
            setPreLoadNumber(0)
            setOnItemClickListener { adapter, view, position ->
                val concernBean = gDataList[position]
                concernBean.isConcern = !concernBean.isConcern
                handleNoConcernData(gDataList)
            }
        }

        //initRecyclerView
        gRecyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridSpacingItemDecoration(4,0, false))
            adapter = gAdapter
        }
    }

    private fun setNoConcernData(){
        repeat(8){
            val concernBean = ConcernBean(it, "https://api.xygeng.cn/Bing/",
                "柠檬",
                false)
            gDataList.add(concernBean)
        }
        handleNoConcernData(gDataList)
    }

    private fun handleNoConcernData(list: ArrayList<ConcernBean>){
        gAdapterList.clear()
        list.forEach {
            if (it.isConcern){
                gAdapterList.add(MultiTypeItem(ConcernGridAdapter.ITEM_CONCERN_GRID_SELECTED, it))
            } else{
                gAdapterList.add(MultiTypeItem(ConcernGridAdapter.ITEM_CONCERN_GRID_UNSELECTED, it))
            }
        }
        gAdapter.replaceData(gAdapterList)
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

            addHeaderView(addConcernHeaderView())
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

    private fun addConcernHeaderView(): View{
        val headerList = ArrayList<MultiTypeItem<Any>>()

        val header = LayoutInflater.from(requireContext()).inflate(R.layout.layout_concern_header, null) as LinearLayout

        val lm = LinearLayoutManager(requireContext())
        lm.orientation = LinearLayoutManager.HORIZONTAL

        val headerRecyclerView = header.findViewById<RecyclerView>(R.id.headerRecyclerView)
        headerRecyclerView.run {
            itemAnimator = DefaultItemAnimator()
            layoutManager = lm
            adapter = headerAdapter
        }

        headerAdapter.run {
            setEnableLoadMore(false)
            setPreLoadNumber(0)
        }

        repeat(10){
            val user = UserBean("https://api.xygeng.cn/Bing/", "Buzz")
            headerList.add(MultiTypeItem(ConcernHeaderAdapter.ITEM_CONCERN_HEADER, user))
        }
        headerAdapter.replaceData(headerList)

        return header
    }

}