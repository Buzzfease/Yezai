package com.jywl.yezai.ui.content.main.liveaction.nearby

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NearbyPresenter @Inject
constructor(): NearbyContract.Presenter {

    private var nearbyView: NearbyContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        nearbyView = view as NearbyContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        nearbyView = null
    }
}