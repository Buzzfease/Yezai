package com.jywl.yezai.ui.content.main.liveaction.concern

import com.jywl.yezai.R
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.main.liveaction.FragmentStripAdapter
import com.jywl.yezai.utils.DisplayUtil
import kotlinx.android.synthetic.main.fragment_live_action.*

class ConcernFragment : BaseMvpFragment<ConcernPresenter>(),
    ConcernContract.View {

    companion object {
        val instance: ConcernFragment
            get() = ConcernFragment()
    }

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_concern
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
    }

}