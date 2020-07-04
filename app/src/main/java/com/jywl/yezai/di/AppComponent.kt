package com.jywl.yezai.di

import dagger.Component
import com.jywl.yezai.utils.glide.GlideCenter
import com.jywl.yezai.utils.network.ApiCenter
import javax.inject.Singleton

@Singleton
@Component(modules = [HttpModule::class, GlideModule::class])
interface AppComponent {
    fun inject(apiCenter: ApiCenter)
    fun inject(glideCenter: GlideCenter)
}