package com.jywl.yezai.ui.content.main.liveaction.team

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TeamPresenter @Inject
constructor(): TeamContract.Presenter {

    private var teamView: TeamContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun takeView(view: Any) {
        teamView = view as TeamContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        teamView = null
    }
}