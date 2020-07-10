package com.jywl.yezai.ui.content.main.shortvideo.tabs

import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.VideoListBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.shortvideo.ShortVideoFragment
import com.jywl.yezai.ui.content.videoplay.VideoPlayActivity
import com.jywl.yezai.ui.widget.SpacesItemDecoration
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_short_video_tabs.*
import timber.log.Timber

/**
 * created by Buzz
 * on 2020/7/6
 * email lmx2060918@126.com
 */
class ShortVideoTabFragment :BaseMvpFragment<ShortVideoTabPresenter>(),
    ShortVideoTabContract.View, BaseQuickAdapter.RequestLoadMoreListener{

    private var params:Int? = 0
    private var onItemClickListener :ShortVideoFragment.OnItemClickListener? = null
    private var mAdapter = ShortVideoCardAdapter()

    fun setOnItemClickListener(onItemClickListener :ShortVideoFragment.OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun beforeOnCreate() {
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_short_video_tabs
    }

    override fun initEventView() {
        //接收的值
        if (arguments != null){
            params = arguments?.getInt("param")
            Timber.e("DynamicFragment" + "接收到了" + params)
        }

        //初始化列表
        initRecyclerView()

        //第一次加载
        Handler().postDelayed({
            setData(false)
        },1000)
    }

    override fun onLoadMoreRequested() {
        Handler().postDelayed({
            setData(true)
        },1000)

        Timber.e("onLoadMoreRequested")
    }

    private fun setData(loadMore :Boolean){
        val list = ArrayList<MultiTypeItem<Any>>()

        val aCard = VideoListBean("Buzz",
            "https://api.xygeng.cn/Bing/",
        "https://api.xygeng.cn/Bing/",
        "https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4",
            227, "自我介绍自我介绍！")

        repeat(10){
            list.add(MultiTypeItem(ShortVideoCardAdapter.ITEM_VIDEO_CARD, aCard))

        }
        if (loadMore) mAdapter.addData(list) else mAdapter.replaceData(list)
        mAdapter.loadMoreComplete()
        Timber.e("setData")
    }

    private fun initRecyclerView(){
        //initHeader
        val playHeader = LayoutInflater.from(requireContext()).inflate(R.layout.layout_video_list_header_play, null) as RelativeLayout
        val selectHeader = LayoutInflater.from(requireContext()).inflate(R.layout.layout_video_list_header_select, null) as LinearLayout
        val secret = selectHeader.findViewById<LinearLayout>(R.id.llSecret)
        secret.setOnClickListener {
            Timber.e("密语被点击了")
        }
        val school = selectHeader.findViewById<LinearLayout>(R.id.llSchool)
        school.setOnClickListener {
            Timber.e("学堂被点击了")
        }
        val randomRelation = selectHeader.findViewById<LinearLayout>(R.id.llRandomRelation)
        randomRelation.setOnClickListener {
            Timber.e("随即速配被点击了")
        }
        playHeader.setOnClickListener {
            Timber.e("视频头被点击了")
        }

        //initAdapter
        mAdapter.run {
            if (params == 1) addHeaderView(selectHeader) else addHeaderView(playHeader)
            setEnableLoadMore(true)
            setPreLoadNumber(0)
            setOnLoadMoreListener(this@ShortVideoTabFragment, recyclerView)
            setOnVideoCardClickListener(object : ShortVideoCardAdapter.OnVideoCardClickListener{
                override fun onVideoCardClick(position: Int) {
                    Timber.e("onVideoCardClick" +  "  position" + position)

                    val intent = Intent(activity, VideoPlayActivity::class.java)
                    startActivity(intent)
                }

                override fun onLikeButtonClick(isLike: Boolean) {
                    Timber.e("onLikeButtonClick" +  "  isLike" + isLike)
                }
            })
        }

        //initRecyclerView
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(SpacesItemDecoration(20))
            adapter = mAdapter
        }
    }
}