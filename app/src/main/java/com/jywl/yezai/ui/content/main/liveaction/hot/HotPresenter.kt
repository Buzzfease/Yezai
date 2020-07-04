package com.jywl.yezai.ui.content.main.liveaction.hot

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HotPresenter @Inject
constructor(): HotContract.Presenter {

    private var hotView: HotContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        hotView = view as HotContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        hotView = null
    }
}