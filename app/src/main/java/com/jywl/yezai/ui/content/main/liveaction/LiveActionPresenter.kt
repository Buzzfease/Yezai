package com.jywl.yezai.ui.content.main.liveaction

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LiveActionPresenter @Inject
constructor(): LiveActionContract.Presenter {

    private var liveActionView: LiveActionContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        liveActionView = view as LiveActionContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        liveActionView = null
    }
}