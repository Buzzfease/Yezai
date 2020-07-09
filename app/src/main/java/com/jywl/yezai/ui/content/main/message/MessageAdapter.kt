package com.jywl.yezai.ui.content.main.message

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean

/**
 * created by Buzz
 * on 2020/7/9
 * email lmx2060918@126.com
 */
class MessageAdapter :BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    companion object {
        const val ITEM_MESSAGE = 0
    }

    init {
        addItemType(ITEM_MESSAGE, R.layout.layout_item_message)
    }


    override fun convert(helper: BaseViewHolder?, item: MultiTypeItem<Any>?) {
        val user = item?.getData() as UserBean
        when (item.itemType) {
            ITEM_MESSAGE -> {

            }
        }
    }
}