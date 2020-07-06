package com.jywl.yezai.ui.content.main.shortvideo.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jywl.yezai.ui.content.main.shortvideo.ShortVideoFragment

class ShortVideoFragmentAdapter (fm: FragmentManager,
                                 private val fragmentArray: Array<ShortVideoFragment.ShortVideoFragmentBean>,
                                 private var onItemClickListener : ShortVideoFragment.OnItemClickListener)
    : FragmentStatePagerAdapter(fm) {
    private var mViews = 0
    private var fragmentMap = HashMap<Int, Fragment>()

    init {
        this.mViews = fragmentArray.size
    }

    override fun getItem(position: Int): Fragment {
        if (position in 0 until mViews) {
            if (fragmentMap[position] == null){
                val fragment = ShortVideoTabFragment()
                fragment.setOnItemClickListener(onItemClickListener)
                val bundle = Bundle()
                bundle.putInt("param", fragmentArray[position].id)
                fragment.arguments = bundle
                fragmentMap[position] = fragment
            }
            return fragmentMap[position] as Fragment
        }
        return ShortVideoTabFragment()
    }

    override fun getCount(): Int {
        return mViews
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position in 0 until mViews) {
            fragmentArray[position].name
        } else null
    }
}