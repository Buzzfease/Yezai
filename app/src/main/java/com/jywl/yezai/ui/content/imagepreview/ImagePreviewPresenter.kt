package com.jywl.yezai.ui.content.imagepreview

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class ImagePreviewPresenter @Inject
constructor(): ImagePreviewContract.Presenter {
    private var imagePreviewView: ImagePreviewContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        imagePreviewView = view as ImagePreviewContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        imagePreviewView = null
    }
}