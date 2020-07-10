package com.jywl.yezai.ui.content.gallery

import com.jywl.yezai.ui.content.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/10
 * email lmx2060918@126.com
 */
class MyGalleryPresenter @Inject
constructor(): MyGalleryContract.Presenter {
    private var myGalleryView: MyGalleryContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        myGalleryView = view as MyGalleryContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        myGalleryView = null
    }
}