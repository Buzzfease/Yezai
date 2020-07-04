package com.jywl.yezai.ui.content.main.recommend

import com.jywl.yezai.utils.network.ApiCenter
import com.jywl.yezai.utils.network.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RecommendPresenter @Inject
constructor(): RecommendContract.Presenter {

    private var recommendView: RecommendContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun testApi() {
        mDisposable.add(
            ApiCenter.get().resApi.test("header","qurey")
                .compose(RxSchedulers.threadIo())
                .compose(RxSchedulers.objectTransformer())
                .subscribe({ objectList ->
                    recommendView?.testApiSuccess()
                },{
                    recommendView?.testApiFailed(it.message)
                }))
    }

    override fun takeView(view: Any) {
        recommendView = view as RecommendContract.View
    }

    override fun dropView() {
        mDisposable.clear()
        recommendView = null
    }
}