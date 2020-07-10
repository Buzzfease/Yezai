package com.jywl.yezai.ui.content.main.liveaction.concern

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.ConcernBean
import com.jywl.yezai.entity.MultiTypeItem

/**
 * created by Buzz
 * on 2020/7/10
 * email lmx2060918@126.com
 */
class ConcernGridAdapter : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    companion object {
        const val ITEM_CONCERN_GRID_SELECTED = 0
        const val ITEM_CONCERN_GRID_UNSELECTED = 1
    }

    init {
        addItemType(ITEM_CONCERN_GRID_SELECTED, R.layout.layout_concern_grid_item_true)
        addItemType(ITEM_CONCERN_GRID_UNSELECTED, R.layout.layout_concern_grid_item_false)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {
        val bean = item?.getData() as ConcernBean
        when (item.itemType) {
            ITEM_CONCERN_GRID_SELECTED -> {

            }
        }
    }
}