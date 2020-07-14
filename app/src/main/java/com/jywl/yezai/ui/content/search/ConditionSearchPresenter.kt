package com.jywl.yezai.ui.content.search

import com.jywl.yezai.ui.content.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class ConditionSearchPresenter @Inject
constructor(): ConditionSearchContract.Presenter {
    private var conditionSearchView: ConditionSearchContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        conditionSearchView = view as ConditionSearchContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        conditionSearchView = null
    }
}