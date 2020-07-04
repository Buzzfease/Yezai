package com.jywl.yezai.ui.content.main.menu

import com.jywl.yezai.ui.content.BasePresenter
import com.jywl.yezai.ui.content.BaseView

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
interface MenuContract {

    interface View: BaseView {
        fun getMenuSuccess()
        fun getMenuFailed()
    }

    interface Presenter: BasePresenter {
        fun getMenu()
    }
}