package com.jywl.yezai.ui.content.main.liveaction.concern

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.ConcernBean
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean

/**
 * created by Buzz
 * on 2020/7/10
 * email lmx2060918@126.com
 */
class ConcernHeaderAdapter : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    companion object {
        const val ITEM_CONCERN_HEADER = 0
    }

    init {
        addItemType(ITEM_CONCERN_HEADER, R.layout.layout_concern_item)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {
        val bean = item?.getData() as UserBean
        when (item.itemType) {
            ITEM_CONCERN_HEADER -> {

            }
        }
    }
}