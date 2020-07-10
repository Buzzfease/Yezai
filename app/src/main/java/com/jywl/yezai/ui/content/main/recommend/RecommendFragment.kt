package com.jywl.yezai.ui.content.main.recommend

import com.jywl.yezai.R
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.widget.TinderStackLayout
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment : BaseMvpFragment<RecommendPresenter>(),
    RecommendContract.View {

    private val mList = ArrayList<UserBean>()

    override fun initInject() {
        getFragmentComponent().inject(this)
    }

    override fun layoutResID(): Int {
        return R.layout.fragment_recommend
    }

    override fun beforeOnCreate() {

    }

    override fun initEventView() {
        //showLoadingDialog()
        //presenter.testApi()

        initCardStack()
        addStackCards()
    }

    private fun initCardStack(){
        tinderStackLayout.setOnStackLoadMoreListener(object: TinderStackLayout.OnStackLoadMoreListener{
            override fun onStackLoadMore() {
                addStackCards()
            }
        })
    }

    private fun addStackCards(){
        repeat(5){
            mList.add(UserBean("https://api.xygeng.cn/Bing/", "Buzz"))
        }
        tinderStackLayout.setDatas(mList)
    }


    override fun testApiSuccess() {
        hideLoadingDialog()
        toast("success")
    }

    override fun testApiFailed(message: String?) {
        hideLoadingDialog()
        toast("failed")
    }


}