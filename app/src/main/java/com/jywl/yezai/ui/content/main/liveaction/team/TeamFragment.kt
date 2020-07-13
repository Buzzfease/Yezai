package com.jywl.yezai.ui.content.main.liveaction.team

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jywl.yezai.R
import com.jywl.yezai.entity.ConcernBean
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.LiveActionAdapter
import com.jywl.yezai.ui.widget.UnderLineTextView
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.android.synthetic.main.fragment_team.recyclerView
import timber.log.Timber

class TeamFragment : BaseMvpFragment<TeamPresenter>(), TeamContract.View {

    private val adapterList = ArrayList<MultiTypeItem<Any>>()
    private val list = ArrayList<ConcernBean>()
    private var jAdapter = TeamJoinAdapter()
    private var hAdapter = TeamHeaderAdapter()
    private var mAdapter = LiveActionAdapter()


    companion object {
        val instance: TeamFragment
            get() = TeamFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_team
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        initJoinRecyclerView()
        setJoinData()

        tvStart.setOnClickListener {
            val aList = list.filter { it.isConcern }
            val array = ArrayList<Int>()
            aList.forEach {
                array.add(it.id)
            }
            toast(array.toString() + "被加入了")


            rlJoinTeam.visibility = View.GONE
            rlTeamList.visibility = View.VISIBLE
            initRecyclerView()
            setData(false)
        }
    }

    private fun initJoinRecyclerView(){
        //initAdapter
        jAdapter.run {
            setEnableLoadMore(false)
            setPreLoadNumber(0)
            val header = LayoutInflater.from(requireContext()).inflate(R.layout.layout_team_join_header, null) as LinearLayout
            val footer = LayoutInflater.from(requireContext()).inflate(R.layout.layout_team_join_footer, null) as UnderLineTextView
            addHeaderView(header)
            addFooterView(footer)
            setOnJoinClickListener(object :TeamJoinAdapter.OnJoinClickListener{
                override fun onJoinClick(hasConcern: Boolean, position: Int) {
                    list[position - 1].isConcern = hasConcern //因为添加了1个固定header
                    handleData()
                    checkIsStartButtonShow()
                }
            })
        }

        //initRecyclerView
        jRecyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jAdapter
        }
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

            addHeaderView(addTeamHeaderView())
        }
        //initRecyclerView
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    private fun setJoinData(){
        repeat(10){
            val user = ConcernBean(it,"https://api.xygeng.cn/Bing/", "Buzz", false)
            list.add(user)
        }
        handleData()
    }

    private fun setData(loadMore :Boolean){
        val list = ArrayList<MultiTypeItem<Any>>()
        val user = UserBean("https://api.xygeng.cn/Bing/", "Buzz")
        repeat(10){
            list.add(MultiTypeItem(LiveActionAdapter.ITEM_TEAM, user))
        }

        if (loadMore) mAdapter.addData(list) else mAdapter.replaceData(list)

        mAdapter.loadMoreComplete()
        Timber.e("setData")
    }

    private fun handleData(){
        adapterList.clear()
        list.forEach {
            if (it.isConcern){
                adapterList.add(MultiTypeItem(TeamJoinAdapter.ITEM_TEAM_HAS_JOIN, it))
            }else{
                adapterList.add(MultiTypeItem(TeamJoinAdapter.ITEM_TEAM_NO_JOIN, it))
            }
        }
        jAdapter.replaceData(adapterList)
    }

    private fun checkIsStartButtonShow(){
        list.forEach {
            if (it.isConcern){
                tvStart.visibility = View.VISIBLE
                return
            }
        }
        tvStart.visibility = View.GONE
    }

    private fun addTeamHeaderView(): View {
        val headerList = ArrayList<MultiTypeItem<Any>>()

        val header = LayoutInflater.from(requireContext()).inflate(R.layout.layout_team_header, null) as LinearLayout

        val lm = LinearLayoutManager(requireContext())
        lm.orientation = LinearLayoutManager.HORIZONTAL

        val headerRecyclerView = header.findViewById<RecyclerView>(R.id.headerRecyclerView)
        headerRecyclerView.run {
            itemAnimator = DefaultItemAnimator()
            layoutManager = lm
            adapter = hAdapter
        }

        hAdapter.run {
            setEnableLoadMore(false)
            setPreLoadNumber(0)
            hAdapter.setOnItemClickListener { adapter, view, position ->
                when(hAdapter.getItem(position)?.itemType){
                    TeamHeaderAdapter.ITEM_TEAM_HEADER_NORMAL ->{
                        toast("header小组点击")
                    }
                    TeamHeaderAdapter.ITEM_TEAM_HEADER_MORE ->{
                        toast("header发现更多")
                    }
                }

            }
        }

        headerList.add(MultiTypeItem(TeamHeaderAdapter.ITEM_TEAM_HEADER_MORE, Any()))
        repeat(5){
            val user = UserBean("https://api.xygeng.cn/Bing/", "Buzz")
            headerList.add(MultiTypeItem(TeamHeaderAdapter.ITEM_TEAM_HEADER_NORMAL, user))
        }
        hAdapter.replaceData(headerList)

        return header
    }
}