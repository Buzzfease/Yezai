package com.jywl.yezai.ui.content.main.liveaction

import androidx.viewpager.widget.ViewPager
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.utils.DisplayUtil
import kotlinx.android.synthetic.main.fragment_live_action.*

class LiveActionFragment : BaseMvpFragment<LiveActionPresenter>(),
    LiveActionContract.View {

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_live_action
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        initTabs()
    }

    private fun initTabs(){
        val mTitles: Array<String> = arrayOf(getString(R.string.tab_nearby),getString(R.string.tab_concern),getString(R.string.tab_hot),getString(R.string.tab_team),getString(R.string.tab_lover))
        val mStripAdapter = LiveActionStripAdapter(childFragmentManager, mTitles)
        viewPager?.adapter = mStripAdapter
        viewPager?.offscreenPageLimit = mTitles.size
        tabStrip?.run {
            //除了这些属性必须在这里设置外，其余的都在style里设置才有效果!
            setNeedChangeTextSize(true)
            setIndicatorPadding(DisplayUtil.dip2px(requireContext(),30f))
            tabBackground = R.drawable.selector_tab_bg
            //必须最后设置viewPager,否则上述没有效果!!
            setViewPager(viewPager as ViewPager)
        }
    }
}