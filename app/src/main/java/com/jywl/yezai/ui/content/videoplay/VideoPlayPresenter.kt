package com.jywl.yezai.ui.content.videoplay

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/7
 * email lmx2060918@126.com
 */
class VideoPlayPresenter @Inject
constructor(): VideoPlayContract.Presenter {
    private var videoPlayView: VideoPlayContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        videoPlayView = view as VideoPlayContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        videoPlayView = null
    }
}