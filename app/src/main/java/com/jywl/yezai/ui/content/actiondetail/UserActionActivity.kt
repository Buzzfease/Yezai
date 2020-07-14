package com.jywl.yezai.ui.content.actiondetail

import android.content.Intent
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.ui.content.main.liveaction.LiveActionAdapter
import com.jywl.yezai.utils.EasyStatusBar
import kotlinx.android.synthetic.main.activity_user_action.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import timber.log.Timber

/**
 * created by Buzz
 * on 2020/07/13
 * email lmx2060918@126.com
 */
class UserActionActivity : BaseMvpActivity<UserActionPresenter>(), UserActionContract.View{

    private var mAdapter = LiveActionAdapter()

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_user_action
    }

    override fun initViewAndEvent() {
        EasyStatusBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)
        tvTitle.text = "个人动态"
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
            val header = LayoutInflater.from(this@UserActionActivity).inflate(R.layout.layout_user_action_header, null) as LinearLayout
            addHeaderView(header)
            setOnItemChildClickListener { _, view, position ->
                val bean = mAdapter.getItem(position)?.getData() as UserBean
                when (view.id) {
                    R.id.tvDetail -> {
                        val intent = Intent(this@UserActionActivity, ActionDetailActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }

        //initRecyclerView
        recyclerView?.run{
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@UserActionActivity)
            adapter = mAdapter
        }
    }


    private fun setData(){
        val list = ArrayList<MultiTypeItem<Any>>()
        repeat(10){
            val user = UserBean("https://api.xygeng.cn/Bing/", "Buzz")
            list.add(MultiTypeItem(LiveActionAdapter.ITEM_USER_ACTION, user))
        }
        mAdapter.replaceData(list)
        Timber.e("setData")
    }
}