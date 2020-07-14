package com.jywl.yezai.ui.content.search

import androidx.core.content.ContextCompat
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.utils.EasyStatusBar
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class SearchResultActivity : BaseMvpActivity<SearchResultPresenter>(), SearchResultContract.View {

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_search_result
    }

    override fun initViewAndEvent() {
        EasyStatusBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary), false)

        ivBack.setOnClickListener { onBackPressed() }
        tvTitle.text = "搜索结果"
    }
}