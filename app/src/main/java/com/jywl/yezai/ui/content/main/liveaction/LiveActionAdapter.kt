package com.jywl.yezai.ui.content.main.liveaction

import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.widget.RoundImageView
import com.jywl.yezai.utils.glide.GlideCenter

/**
 * created by Buzz
 * on 2020/7/9
 * email lmx2060918@126.com
 */
class LiveActionAdapter : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    companion object {
        const val ITEM_ACTION = 0
    }

    init {
        addItemType(ITEM_ACTION, R.layout.layout_dongtai_item)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {
        val user = item?.getData() as UserBean
        when (item.itemType) {
            ITEM_ACTION -> {
                val tvName = helper.getView<TextView>(R.id.tvUserName)
                val ivAvatar = helper.getView<RoundImageView>(R.id.ivAvatar)
                tvName.text = user.userName
                GlideCenter.get().showCrossFadeImage(ivAvatar, user.userAvatar)
            }
        }
    }
}