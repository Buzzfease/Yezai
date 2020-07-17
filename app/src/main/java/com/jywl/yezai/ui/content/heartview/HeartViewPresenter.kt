package com.jywl.yezai.ui.content.heartview

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/17
 * email lmx2060918@126.com
 */
class HeartViewPresenter @Inject
constructor(): HeartViewContract.Presenter {
    private var heartView: HeartViewContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        heartView = view as HeartViewContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        heartView = null
    }
}