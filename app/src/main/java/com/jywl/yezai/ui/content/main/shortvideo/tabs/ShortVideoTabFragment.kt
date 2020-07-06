package com.jywl.yezai.ui.content.main.shortvideo.tabs

import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.VideoListBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.shortvideo.ShortVideoFragment
import com.jywl.yezai.ui.widget.GridSpacingItemDecoration
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
        //Top
        test.text = (params.toString())
        if (params == 1){
            llTop.visibility = View.VISIBLE
        }else{
            llTop.visibility = View.GONE
        }
        //
        initRecyclerView()

        Handler().postDelayed({
            setData()
        },1000)
    }

    override fun onLoadMoreRequested() {
        Handler().postDelayed({
            setData()
        },1000)

        Timber.e("onLoadMoreRequested")
    }

    private fun setData(){
        val list = ArrayList<MultiTypeItem<Any>>()

        val aCard = VideoListBean("Buzz",
            "https://api.xygeng.cn/Bing/",
        "https://api.xygeng.cn/Bing/",
        "https://gslb.miaopai.com/stream/IR3oMYDhrON5huCmf7sHCfnU5YKEkgO2.mp4",
            227, "自我介绍自我介绍！")

        repeat(10){
            list.add(MultiTypeItem(ShortVideoCardAdapter.ITEM_VIDEO_CARD, aCard))
        }

        mAdapter.replaceData(list)
        mAdapter.loadMoreComplete()
        Timber.e("setData")
    }

    private fun initRecyclerView(){
        mAdapter.run {
            setEnableLoadMore(true)
            setPreLoadNumber(0)
            setOnLoadMoreListener(this@ShortVideoTabFragment, recyclerView)
            setOnVideoCardClickListener(object : ShortVideoCardAdapter.OnVideoCardClickListener{
                override fun onVideoCardClick(position: Int) {
                    Timber.e("onVideoCardClick" +  "  position" + position)
                }

                override fun onLikeButtonClick(isLike: Boolean) {
                    Timber.e("onLikeButtonClick" +  "  isLike" + isLike)
                }
            })
        }
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridSpacingItemDecoration(2,20, true))
            adapter = mAdapter
        }
    }
}