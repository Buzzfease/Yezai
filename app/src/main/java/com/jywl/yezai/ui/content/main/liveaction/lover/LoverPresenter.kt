package com.jywl.yezai.ui.content.main.liveaction.lover

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoverPresenter @Inject
constructor(): LoverContract.Presenter {

    private var loverView: LoverContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        loverView = view as LoverContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        loverView = null
    }
}