package com.jywl.yezai.ui.content.actiondetail

import com.jywl.yezai.ui.content.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/13
 * email lmx2060918@126.com
 */
class ActionDetailPresenter @Inject
constructor(): ActionDetailContract.Presenter {
    private var actionDetailView: ActionDetailContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        actionDetailView = view as ActionDetailContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        actionDetailView = null
    }
}