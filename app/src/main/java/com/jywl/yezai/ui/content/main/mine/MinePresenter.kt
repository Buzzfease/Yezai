package com.jywl.yezai.ui.content.main.mine

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MinePresenter @Inject
constructor(): MineContract.Presenter {

    private var mineView: MineContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        mineView = view as MineContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        mineView = null
    }
}