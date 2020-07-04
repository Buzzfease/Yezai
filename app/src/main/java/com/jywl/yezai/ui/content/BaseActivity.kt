package com.jywl.yezai.ui.content

import android.app.Dialog
import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.jywl.yezai.ActivityManager
import com.jywl.yezai.MyApplication
import com.jywl.yezai.R
import com.jywl.yezai.utils.DensityUtil
import com.jywl.yezai.utils.DialogFactory
import com.jywl.yezai.utils.EasyToast

/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
abstract class BaseActivity: RxAppCompatActivity(){
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        ActivityManager.pushActivity(this)
        DensityUtil.setCustomDensity(this, MyApplication.instance())
        setContentView(layoutResID())
        onViewCreated()
        initViewAndEvent()
    }

    open fun onViewCreated(){

    }

    open fun beforeOnCreate(){

    }

    abstract fun layoutResID(): Int

    abstract fun initViewAndEvent()

    fun toast(content: String?) {
        if (!content.isNullOrEmpty()){
            EasyToast.newBuilder(R.layout.view_toast, R.id.tvToast).build().show(content)
        }
    }

    fun showLoadingDialog() {
        loadingDialog = DialogFactory.getLoadingDialog(this, getString(R.string.please_wait))
    }

    fun showLoadingDialog(str: String?) {
        loadingDialog = DialogFactory.getLoadingDialog(this, str)
    }

    fun hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing){
            loadingDialog?.dismiss()
        }
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null
        super.onDestroy()
        ActivityManager.popActivity(this)
    }
}