package com.jywl.yezai.ui.content.main.liveaction.concern

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ConcernPresenter @Inject
constructor(): ConcernContract.Presenter {

    private var concernView: ConcernContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        concernView = view as ConcernContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        concernView = null
    }
}