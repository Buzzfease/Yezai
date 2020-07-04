package com.jywl.yezai.ui.content.main.recommend

import com.jywl.yezai.ui.content.BasePresenter
import com.jywl.yezai.ui.content.BaseView

interface RecommendContract {

    interface View: BaseView {
        fun testApiSuccess()
        fun testApiFailed(message:String?)
    }

    interface Presenter: BasePresenter {
        fun testApi(){}
    }

}