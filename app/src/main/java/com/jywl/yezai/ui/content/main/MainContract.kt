package com.jywl.yezai.ui.content.main

import com.jywl.yezai.ui.content.BasePresenter
import com.jywl.yezai.ui.content.BaseView

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
interface MainContract {

    interface View: BaseView {
        fun getMenuSuccess()
        fun getMenuFailed(message:String?)
    }

    interface Presenter: BasePresenter {
        fun getMenu()
    }
}