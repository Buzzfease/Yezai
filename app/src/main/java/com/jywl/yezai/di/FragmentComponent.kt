package com.jywl.yezai.di

import dagger.Component
import com.jywl.yezai.ui.content.main.menu.MenuFragment

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {
    fun inject(mineFragment: MenuFragment)
}