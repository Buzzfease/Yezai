package com.jywl.yezai.ui.content.main.liveaction.team

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.lover.LoverFragment

class TeamFragment : BaseMvpFragment<TeamPresenter>(),
    TeamContract.View {

    companion object {
        val instance: TeamFragment
            get() = TeamFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_team
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }

}