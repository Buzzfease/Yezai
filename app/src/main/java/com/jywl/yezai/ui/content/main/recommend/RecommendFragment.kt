package com.jywl.yezai.ui.content.main.recommend

import android.content.Intent
import android.view.View
import com.jywl.yezai.FinalParams
import com.jywl.yezai.R
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.content.BaseMvpFragment
import com.jywl.yezai.ui.content.actiondetail.UserActionActivity
import com.jywl.yezai.ui.content.gallery.MyGalleryActivity
import com.jywl.yezai.ui.widget.TinderStackLayout
import com.jywl.yezai.utils.EasyStatusBar
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
        tinderStackLayout.setOnStackChildClickListener(object :TinderStackLayout.OnStackChildClickListener{
            override fun onStackChildClick(userBean: UserBean?, position: Int, view: View) {
                when(view.id){
                    R.id.tvMoreAction ->{
                        toast("第" + position.toString() + "张卡的查看更多被点击了")
                        val intent = Intent(activity, UserActionActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }

    private fun addStackCards(){
        repeat(5){
            mList.add(UserBean("https://api.xygeng.cn/Bing/", "Buzz"))
        }
        tinderStackLayout.setData(mList)
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