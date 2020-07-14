package com.jywl.yezai.di

import com.jywl.yezai.ui.content.main.liveaction.LiveActionFragment
import com.jywl.yezai.ui.content.main.liveaction.concern.ConcernFragment
import com.jywl.yezai.ui.content.main.liveaction.hot.HotFragment
import com.jywl.yezai.ui.content.main.liveaction.lover.LoverFragment
import com.jywl.yezai.ui.content.main.liveaction.nearby.NearByFragment
import com.jywl.yezai.ui.content.main.liveaction.team.TeamFragment
import com.jywl.yezai.ui.content.main.message.MessageFragment
import com.jywl.yezai.ui.content.main.mine.MineFragment
import com.jywl.yezai.ui.content.main.recommend.RecommendFragment
import com.jywl.yezai.ui.content.main.shortvideo.ShortVideoFragment
import com.jywl.yezai.ui.content.main.shortvideo.tabs.ShortVideoTabFragment
import com.jywl.yezai.ui.content.search.ConditionSearchFragment
import com.jywl.yezai.ui.content.search.TargetSearchFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {
    //首页tab
    fun inject(recommendFragment: RecommendFragment)
    fun inject(shortVideoFragment: ShortVideoFragment)
    fun inject(liveActionFragment: LiveActionFragment)
    fun inject(messageFragment: MessageFragment)
    fun inject(mineFragment: MineFragment)

    //动态页tab
    fun inject(nearByFragment: NearByFragment)
    fun inject(concernFragment: ConcernFragment)
    fun inject(hotFragment: HotFragment)
    fun inject(teamFragment: TeamFragment)
    fun inject(loverFragment: LoverFragment)
    fun inject(shortVideoTabFragment: ShortVideoTabFragment)

    //搜索页tab
    fun inject(conditionSearchFragment: ConditionSearchFragment)
    fun inject(targetSearchFragment: TargetSearchFragment)
}