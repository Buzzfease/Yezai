package com.jywl.yezai.ui.content.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class SearchStripAdapter (fm: FragmentManager, private val titles: Array<String>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private var mViews = 0
    private var conditionSearchFragment: ConditionSearchFragment? = null
    private var targetSearchFragment: TargetSearchFragment? = null

    init {
        this.mViews = titles.size
    }

    override fun getItem(position: Int): Fragment {
        if (position in 0 until mViews) {
            when (position) {
                CONDITION -> {
                    if (conditionSearchFragment == null)
                        conditionSearchFragment = ConditionSearchFragment.instance
                    return conditionSearchFragment as Fragment
                }
                TARGET ->{
                    if (targetSearchFragment == null)
                        targetSearchFragment = TargetSearchFragment.instance
                    return targetSearchFragment as Fragment
                }
                else -> {
                }
            }
        }
        return conditionSearchFragment as Fragment
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
        private const val CONDITION = 0//条件搜索
        private const val TARGET = 1 //精确搜索
    }
}
