package com.jywl.yezai.ui.content.search

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class SearchResultPresenter @Inject
constructor(): SearchResultContract.Presenter {
    private var searchResultView: SearchResultContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        searchResultView = view as SearchResultContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        searchResultView = null
    }
}