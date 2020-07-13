package com.jywl.yezai.ui.content.main.liveaction.team

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem

/**
 * created by Buzz
 * on 2020/7/13
 * email lmx2060918@126.com
 */
class TeamHeaderAdapter : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    companion object {
        const val ITEM_TEAM_HEADER_NORMAL = 0
        const val ITEM_TEAM_HEADER_MORE = 1
    }

    init {
        addItemType(ITEM_TEAM_HEADER_NORMAL, R.layout.layout_team_header_normal)
        addItemType(ITEM_TEAM_HEADER_MORE, R.layout.layout_team_header_more)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {

    }
}