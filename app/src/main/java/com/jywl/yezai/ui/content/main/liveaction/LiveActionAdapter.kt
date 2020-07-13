package com.jywl.yezai.ui.content.main.liveaction

import android.view.View
import android.widget.LinearLayout
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
        const val ITEM_NEARBY = 0
        const val ITEM_CONCERN = 1
        const val ITEM_HOT = 2
        const val ITEM_TEAM = 3
        const val ITEM_LOVER = 4
    }

    init {
        addItemType(ITEM_NEARBY, R.layout.layout_dongtai_item)
        addItemType(ITEM_CONCERN, R.layout.layout_dongtai_item)
        addItemType(ITEM_HOT, R.layout.layout_dongtai_item)
        addItemType(ITEM_TEAM, R.layout.layout_dongtai_item)
        addItemType(ITEM_LOVER, R.layout.layout_dongtai_item)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {
        val user = item?.getData() as UserBean
        val tvName = helper.getView<TextView>(R.id.tvUserName)
        val ivAvatar = helper.getView<RoundImageView>(R.id.ivAvatar)
        tvName.text = user.userName
        GlideCenter.get().showCrossFadeImage(ivAvatar, user.userAvatar)

        when (item.itemType) {
            ITEM_TEAM -> {
                helper.getView<LinearLayout>(R.id.llTeam).visibility = View.VISIBLE
                helper.getView<LinearLayout>(R.id.llComment).visibility = View.VISIBLE
            }
        }
    }
}