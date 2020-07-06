package com.jywl.yezai.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

class MultiTypeItem<T>(private val itemType: Int, private val data: T) : MultiItemEntity {

    override fun getItemType(): Int {
        return itemType
    }

    fun getData(): T {
        return data
    }
}