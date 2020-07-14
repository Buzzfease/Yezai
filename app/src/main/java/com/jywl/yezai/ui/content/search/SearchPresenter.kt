package com.jywl.yezai.ui.content.search

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class SearchPresenter @Inject
constructor(): SearchContract.Presenter {
    private var searchView: SearchContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        searchView = view as SearchContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        searchView = null
    }
}