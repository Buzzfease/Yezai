package com.jywl.yezai.di

import com.jywl.yezai.ui.content.actiondetail.ActionDetailActivity
import com.jywl.yezai.ui.content.actiondetail.UserActionActivity
import com.jywl.yezai.ui.content.gallery.MyGalleryActivity
import com.jywl.yezai.ui.content.heartview.HeartViewActivity
import com.jywl.yezai.ui.content.imagepreview.ImagePreviewActivity
import dagger.Component
import com.jywl.yezai.ui.content.main.MainActivity
import com.jywl.yezai.ui.content.search.SearchActivity
import com.jywl.yezai.ui.content.search.SearchResultActivity
import com.jywl.yezai.ui.content.videoplay.VideoPlayActivity

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(videoPlayActivity: VideoPlayActivity)
    fun inject(myGalleryActivity: MyGalleryActivity)
    fun inject(userActionActivity: UserActionActivity)
    fun inject(actionDetailActivity: ActionDetailActivity)
    fun inject(imagePreviewActivity: ImagePreviewActivity)
    fun inject(searchActivity: SearchActivity)
    fun inject(searchResultActivity: SearchResultActivity)
    fun inject(heartViewActivity: HeartViewActivity)
}