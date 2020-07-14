package com.jywl.yezai.ui.content.actiondetail

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem

/**
 * created by Buzz
 * on 2020/7/14
 * email lmx2060918@126.com
 */
class CommentAdapter  : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    companion object {
        const val ITEM_ACTION_COMMENT = 0
    }

    init {
        addItemType(ITEM_ACTION_COMMENT, R.layout.layout_comment_item)
    }


    override fun convert(helper: BaseViewHolder?, item: MultiTypeItem<Any>?) {

    }
}