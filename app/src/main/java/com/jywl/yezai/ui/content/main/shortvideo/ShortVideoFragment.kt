package com.jywl.yezai.ui.content.main.shortvideo

import android.os.Parcelable
import androidx.viewpager.widget.ViewPager
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.shortvideo.tabs.ShortVideoFragmentAdapter
import com.jywl.yezai.utils.DisplayUtil
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_short_video.*

class ShortVideoFragment : BaseMvpFragment<ShortVideoPresenter>(),
    ShortVideoContract.View {

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_short_video
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        val list = ArrayList<ShortVideoFragmentBean>()
        list.add(ShortVideoFragmentBean(0, getString(R.string.tab_short_video_concern)))
        list.add(ShortVideoFragmentBean(1, getString(R.string.tab_short_video_recommend)))
        list.add(ShortVideoFragmentBean(2, getString(R.string.tab_short_video_hot)))
        initTabs(list)
    }

    private fun initTabs(list: List<ShortVideoFragmentBean>){
        //adapter
        //这里其实应该判断后端返回name为null的问题
        val mArrays = list.toTypedArray()
        val mStripAdapter = ShortVideoFragmentAdapter(childFragmentManager, mArrays, object :OnItemClickListener{
            override fun onTabFragmentItemClickListener(resId: Int, position: Int) {

            }
        })
        viewPager?.adapter = mStripAdapter
        viewPager?.offscreenPageLimit = mArrays.size
        tabStrip?.run {
            //除了这些属性必须在这里设置外，其余的都在style里设置才有效果!
            setNeedChangeTextSize(true)
            setIndicatorPadding(DisplayUtil.dip2px(requireContext(),30f))
            tabBackground = R.drawable.selector_tab_bg
            //必须最后设置viewPager,否则上述没有效果!!
            setViewPager(viewPager as ViewPager)
        }
    }

    interface OnItemClickListener{
        fun onTabFragmentItemClickListener(resId:Int, position:Int)
    }

    @Parcelize
    data class ShortVideoFragmentBean(var id: Int = 0, var name:String? = null):Parcelable
}