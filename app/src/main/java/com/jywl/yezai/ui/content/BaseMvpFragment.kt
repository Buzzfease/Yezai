package com.jywl.yezai.ui.content

import android.os.Bundle
import android.view.View
import com.jywl.yezai.MyApplication
import com.jywl.yezai.di.DaggerFragmentComponent
import com.jywl.yezai.di.FragmentComponent

import javax.inject.Inject


/**
 * Created by Buzz on 2019/7/1.
 * Email :lmx2060918@126.com
 */
abstract class BaseMvpFragment<T: BasePresenter>: BaseFragment() {
    @Inject
    lateinit var presenter:T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInject()
        presenter.takeView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

    protected fun getFragmentComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
                .appComponent(MyApplication.appComponent)
                .build()
    }

    abstract fun initInject()
}