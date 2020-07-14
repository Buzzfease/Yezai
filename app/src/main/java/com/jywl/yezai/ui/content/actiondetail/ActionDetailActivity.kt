package com.jywl.yezai.ui.content.actiondetail

import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.ui.widget.MyMultiStateView
import com.jywl.yezai.utils.EasyStatusBar
import kotlinx.android.synthetic.main.activity_action_detail.*
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * created by Buzz
 * on 2020/7/13
 * email lmx2060918@126.com
 */
class ActionDetailActivity: BaseMvpActivity<ActionDetailPresenter>(), ActionDetailContract.View {

    private var mAdapter = CommentAdapter()

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_action_detail
    }

    override fun initViewAndEvent() {
        EasyStatusBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)
        tvTitle.text = "柠檬"
        ivBack.setOnClickListener {
            onBackPressed()
        }

        initRecyclerView()
        setData()
    }

    private fun initRecyclerView(){
        //initAdapter
        mAdapter.run {
            setEnableLoadMore(false)
            setPreLoadNumber(0)
            val header = LayoutInflater.from(this@ActionDetailActivity).inflate(R.layout.layout_dongtai_detail, null) as LinearLayout
            val container = header.findViewById<MyMultiStateView>(R.id.picContainer)
            val imageList = ArrayList<Any>()
            repeat((1..9).random()){
                imageList.add("https://api.xygeng.cn/Bing/")
            }
            container.setImageData(imageList)
            addHeaderView(header)
        }

        //initRecyclerView
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@ActionDetailActivity)
            adapter = mAdapter
        }
    }

    private fun setData(){
        val list = ArrayList<MultiTypeItem<Any>>()
        repeat(10){
            val user = UserBean("https://api.xygeng.cn/Bing/", "Buzz")
            list.add(MultiTypeItem(CommentAdapter.ITEM_ACTION_COMMENT, user))
        }
        mAdapter.replaceData(list)
    }
}