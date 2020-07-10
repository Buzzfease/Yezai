package com.jywl.yezai.di

import com.jywl.yezai.ui.content.gallery.MyGalleryActivity
import dagger.Component
import com.jywl.yezai.ui.content.main.MainActivity
import com.jywl.yezai.ui.content.videoplay.VideoPlayActivity

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(videoPlayActivity: VideoPlayActivity)
    fun inject(myGalleryActivity: MyGalleryActivity)
}