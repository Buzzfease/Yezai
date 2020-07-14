package com.jywl.yezai.ui.content.search

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class TargetSearchPresenter @Inject
constructor(): TargetSearchContract.Presenter {
    private var targetSearchView: TargetSearchContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        targetSearchView = view as TargetSearchContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        targetSearchView = null
    }
}