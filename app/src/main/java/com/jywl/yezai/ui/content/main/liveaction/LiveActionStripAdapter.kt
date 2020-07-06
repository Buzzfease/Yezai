package com.jywl.yezai.ui.content.main.liveaction

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jywl.yezai.ui.content.main.liveaction.concern.ConcernFragment
import com.jywl.yezai.ui.content.main.liveaction.hot.HotFragment
import com.jywl.yezai.ui.content.main.liveaction.lover.LoverFragment
import com.jywl.yezai.ui.content.main.liveaction.nearby.NearByFragment
import com.jywl.yezai.ui.content.main.liveaction.team.TeamFragment

class LiveActionStripAdapter(fm: FragmentManager, private val titles: Array<String>) : FragmentStatePagerAdapter(fm) {


    private var mViews = 0
    private var nearByFragment :NearByFragment? = null
    private var concernFragment: ConcernFragment? = null
    private var hotFragment: HotFragment? = null
    private var teamFragment: TeamFragment? = null
    private var loverFragment:LoverFragment? = null

    init {
        this.mViews = titles.size
    }

    override fun getItem(position: Int): Fragment {
        if (position in 0 until mViews) {
            when (position) {
                NEARBY -> {
                    if (nearByFragment == null)
                        nearByFragment = NearByFragment.instance
                    return nearByFragment as Fragment
                }
                CONCERN ->{
                    if (concernFragment == null)
                        concernFragment = ConcernFragment.instance
                    return concernFragment as Fragment
                }
                HOT ->{
                    if (hotFragment == null)
                        hotFragment = HotFragment.instance
                    return hotFragment as Fragment
                }
                TEAM -> {
                    if (teamFragment == null)
                        teamFragment = TeamFragment.instance
                    return teamFragment as Fragment
                }
                LOVER -> {
                    if (loverFragment == null)
                        loverFragment = LoverFragment.instance
                    return loverFragment as Fragment
                }
                else -> {
                }
            }
        }
        return nearByFragment as Fragment
    }

    override fun getCount(): Int {
        return mViews
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position in 0 until mViews) {
            titles[position]
        } else null
    }

    companion object {
        private const val NEARBY = 0//附近
        private const val CONCERN = 1 //关注
        private const val HOT = 2//热门
        private const val TEAM = 3//小组
        private const val LOVER = 4//情侣
    }
}
