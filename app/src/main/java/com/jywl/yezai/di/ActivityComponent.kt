package com.jywl.yezai.di

import dagger.Component
import com.jywl.yezai.ui.content.main.MainActivity

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}