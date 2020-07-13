package com.jywl.yezai.ui.content.main.liveaction

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.UserBean
import com.jywl.yezai.ui.widget.MyMultiStateView
import com.jywl.yezai.ui.widget.RoundImageView
import com.jywl.yezai.ui.widget.WidthSquareImageView
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

        //三宫格
        initPicContainer(helper, (1..9).random())

        when (item.itemType) {
            ITEM_TEAM -> {
                helper.getView<LinearLayout>(R.id.llTeam).visibility = View.VISIBLE
                helper.getView<LinearLayout>(R.id.llComment).visibility = View.VISIBLE
            }
            ITEM_LOVER ->{
                helper.getView<LinearLayout>(R.id.llLover).visibility = View.VISIBLE
                helper.getView<LinearLayout>(R.id.llUser).visibility = View.GONE
            }
        }
    }

    private fun initPicContainer(helper: BaseViewHolder, picCount: Int){
        val picContainer = helper.getView<MyMultiStateView>(R.id.picContainer)
        val pic1 = helper.getView<WidthSquareImageView>(R.id.ivPic1)
        val pic2 = helper.getView<WidthSquareImageView>(R.id.ivPic2)
        val pic3 = helper.getView<WidthSquareImageView>(R.id.ivPic3)
        val tvCount = helper.getView<TextView>(R.id.tvCount)
        when {
            picCount == 1 -> {
                picContainer.setViewStatus(MyMultiStateView.ViewStatus.onePicStatus)
                GlideCenter.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar)
            }
            picCount == 2 -> {
                picContainer.setViewStatus(MyMultiStateView.ViewStatus.twoPicStatus)
                GlideCenter.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar)
                GlideCenter.get().showCrossFadeImage(pic2, R.mipmap.ic_avatar)
            }
            picCount == 3 -> {
                picContainer.setViewStatus(MyMultiStateView.ViewStatus.threePicStatus)
                GlideCenter.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar)
                GlideCenter.get().showCrossFadeImage(pic2, R.mipmap.ic_avatar)
                GlideCenter.get().showCrossFadeImage(pic3, R.mipmap.ic_avatar)
            }
            picCount > 3 -> {
                picContainer.setViewStatus(MyMultiStateView.ViewStatus.moreThanThreePicStatus)
                GlideCenter.get().showCrossFadeImage(pic1, R.mipmap.ic_avatar)
                GlideCenter.get().showCrossFadeImage(pic2, R.mipmap.ic_avatar)
                GlideCenter.get().showCrossFadeImage(pic3, R.mipmap.ic_avatar)
                tvCount.visibility = View.VISIBLE
                tvCount.text = "共" + picCount + "张"
            }
        }
    }
}