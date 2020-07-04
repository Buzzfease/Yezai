package com.jywl.yezai.ui.content.main.message

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MessagePresenter @Inject
constructor(): MessageContract.Presenter {

    private var messageView: MessageContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        messageView = view as MessageContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        messageView = null
    }
}