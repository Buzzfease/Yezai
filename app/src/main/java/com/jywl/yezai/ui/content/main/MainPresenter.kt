package com.jywl.yezai.ui.content.main

import io.reactivex.disposables.CompositeDisposable
import com.jywl.yezai.utils.network.ApiCenter
import com.jywl.yezai.utils.network.RxSchedulers
import javax.inject.Inject

/**
 * Created by Buzz on 2019/12/17.
 * Email :lmx2060918@126.com
 */
class MainPresenter @Inject
constructor():MainContract.Presenter {
    private var mainView:MainContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        mainView = view as MainContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        mainView = null
    }
}