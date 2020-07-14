package com.jywl.yezai.ui.content.actiondetail

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * created by Buzz
 * on 2020/7/13
 * email lmx2060918@126.com
 */
class UserActionPresenter  @Inject
constructor(): UserActionContract.Presenter {
    private var userActionView: UserActionContract.View? = null
    private val mDisposable = CompositeDisposable()


    override fun takeView(view: Any) {
        userActionView = view as UserActionContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        userActionView = null
    }
}