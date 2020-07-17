package com.jywl.yezai.ui.content.search

import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.utils.DisplayUtil
import com.jywl.yezai.utils.EasyStatusBar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_live_action.tabStrip
import kotlinx.android.synthetic.main.fragment_live_action.viewPager

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class SearchActivity :BaseMvpActivity<SearchPresenter>(), SearchContract.View {

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_search
    }

    override fun initViewAndEvent() {
        EasyStatusBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary),false)
        initTabs()
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initTabs(){
        val mTitles: Array<String> = arrayOf(getString(R.string.tab_condition_search),getString(R.string.tab_target_search))
        val mStripAdapter = SearchStripAdapter(supportFragmentManager, mTitles)
        viewPager?.adapter = mStripAdapter
        viewPager?.offscreenPageLimit = mTitles.size
        tabStrip?.run {
            //除了这些属性必须在这里设置外，其余的都在style里设置才有效果!
            setNeedChangeTextSize(false)
            setIndicatorPadding(DisplayUtil.dip2px(this@SearchActivity,38f))
            tabBackground = R.drawable.selector_tab_bg
            //必须最后设置viewPager,否则上述没有效果!!
            setViewPager(viewPager as ViewPager)
        }
    }
}