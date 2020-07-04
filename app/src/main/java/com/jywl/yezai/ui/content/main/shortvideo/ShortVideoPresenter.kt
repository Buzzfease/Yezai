package com.jywl.yezai.ui.content.main.shortvideo

import com.jywl.yezai.ui.content.main.shortvideo.ShortVideoContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ShortVideoPresenter @Inject
constructor(): ShortVideoContract.Presenter{

    private var shortVideoView: ShortVideoContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        shortVideoView = view as ShortVideoContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        shortVideoView = null
    }
}