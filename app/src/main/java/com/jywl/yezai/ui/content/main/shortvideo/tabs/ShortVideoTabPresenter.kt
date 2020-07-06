package com.jywl.yezai.ui.content.main.shortvideo.tabs

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/6
 * email lmx2060918@126.com
 */
class ShortVideoTabPresenter @Inject
constructor(): ShortVideoTabContract.Presenter {

    private var shortVideoTabView: ShortVideoTabContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        shortVideoTabView = view as ShortVideoTabContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        shortVideoTabView = null
    }
}