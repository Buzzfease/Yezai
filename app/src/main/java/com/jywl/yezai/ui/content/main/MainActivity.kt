package com.jywl.yezai.ui.content.main

import kotlinx.android.synthetic.main.activity_main.*
import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpActivity
import com.jywl.yezai.utils.EasyLog
import com.jywl.yezai.utils.EasyStatusBar
import com.jywl.yezai.utils.glide.GlideCenter

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
        EasyStatusBar.makeStatusBarTransparent(this, true, rlMain, avatar)
        showLoadingDialog()
        GlideCenter.get().showCircleImage(avatar, R.mipmap.ic_avatar)
        GlideCenter.get().showCrossFadeImage(pic, R.mipmap.ic_load_circle)
        presenter.getMenu()
    }

    override fun getMenuSuccess() {
        EasyLog.DEFAULT.e("success")
        hideLoadingDialog()
        toast("success")
    }

    override fun getMenuFailed(message:String?) {
        EasyLog.DEFAULT.e("message")
        hideLoadingDialog()
        toast("message")
    }

}