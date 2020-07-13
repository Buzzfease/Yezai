package com.jywl.yezai.ui.content.main.liveaction.team

import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.ConcernBean
import com.jywl.yezai.entity.MultiTypeItem

/**
 * created by Buzz
 * on 2020/7/13
 * email lmx2060918@126.com
 */
class TeamJoinAdapter : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    private var onJoinClickListener:OnJoinClickListener? = null
    fun setOnJoinClickListener(listener: OnJoinClickListener){
        this.onJoinClickListener = listener
    }

    companion object {
        const val ITEM_TEAM_HAS_JOIN = 0
        const val ITEM_TEAM_NO_JOIN = 1
    }

    init {
        addItemType(ITEM_TEAM_HAS_JOIN, R.layout.layout_team_item_true)
        addItemType(ITEM_TEAM_NO_JOIN, R.layout.layout_team_item_false)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {
        val bean = item?.getData() as ConcernBean
        val tvJoin = helper.getView<TextView>(R.id.tvJoin)
        when (item.itemType) {
            ITEM_TEAM_HAS_JOIN -> {
                tvJoin.setOnClickListener {
                    onJoinClickListener?.onJoinClick(false, helper.adapterPosition)
                }
            }
            ITEM_TEAM_NO_JOIN -> {
                tvJoin.setOnClickListener {
                    onJoinClickListener?.onJoinClick(true, helper.adapterPosition)
                }
            }
        }
    }

    interface OnJoinClickListener{
        fun onJoinClick(hasConcern :Boolean, position:Int)
    }
}