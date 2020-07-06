package com.jywl.yezai.ui.content.main

import androidx.core.content.ContextCompat
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.ui.content.main.liveaction.LiveActionFragment
import com.jywl.yezai.ui.content.main.message.MessageFragment
import com.jywl.yezai.ui.content.main.mine.MineFragment
import com.jywl.yezai.ui.content.main.recommend.RecommendFragment
import com.jywl.yezai.ui.content.main.shortvideo.ShortVideoFragment
import com.jywl.yezai.utils.EasyStatusBar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.View{

    override fun initInject() {
        getActivityComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initViewAndEvent() {
        EasyStatusBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white), true)
        initBottomBar()
        //EasyStatusBar.makeStatusBarTransparent(this, true, llM, avatar)

//        GlideCenter.get().showCircleImage(avatar, R.mipmap.ic_avatar)
//        GlideCenter.get().showCrossFadeImage(pic, R.mipmap.ic_load_circle)
    }

    private fun initBottomBar(){
        bottomBar.setContainer(R.id.container)
            .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
            .addItem(
                RecommendFragment::class.java,
                getString(R.string.tab_recommend),
                R.mipmap.tab_tuijian_pre,
                R.mipmap.tab_tuijian_pre)
            .addItem(
                ShortVideoFragment::class.java,
                getString(R.string.tab_short_video),
                R.mipmap.tab_duanshipin_normal,
                R.mipmap.tab_duanshipin_normal)
            .addItem(
                LiveActionFragment::class.java,
                getString(R.string.tab_live_action),
                R.mipmap.tab_dongtai_normal,
                R.mipmap.tab_dongtai_normal)
            .addItem(
                MessageFragment::class.java,
                getString(R.string.tab_message),
                R.mipmap.tab_xiaoxi_normal,
                R.mipmap.tab_xiaoxi_normal)
            .addItem(
                MineFragment::class.java,
                getString(R.string.tab_mine),
                R.mipmap.tab_wo_normal,
                R.mipmap.tab_wo_normal)
            .setFirstChecked(0)
            .build()
    }
}