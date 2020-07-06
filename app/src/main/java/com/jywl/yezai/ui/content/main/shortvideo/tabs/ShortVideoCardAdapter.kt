package com.jywl.yezai.ui.content.main.shortvideo.tabs

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.entity.VideoListBean
import com.jywl.yezai.ui.widget.RoundImageView
import com.jywl.yezai.utils.glide.GlideCenter
import com.like.LikeButton
import com.like.OnLikeListener

class ShortVideoCardAdapter : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {
    private var onVideoCardClickListener :OnVideoCardClickListener? = null

    companion object{
        const val ITEM_VIDEO_CARD = 0
    }

    fun setOnVideoCardClickListener(onVideoCardClickListener :OnVideoCardClickListener){
        this.onVideoCardClickListener = onVideoCardClickListener
    }

    init {
        addItemType(ITEM_VIDEO_CARD, R.layout.layout_video_card)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {
        val bean = item?.getData() as VideoListBean
        var likeCount = bean.likeCount
        when(item.itemType){
            ITEM_VIDEO_CARD ->{
                val cardVideo = helper.getView<CardView>(R.id.cardVideo)
                cardVideo.setOnClickListener {
                    onVideoCardClickListener?.onVideoCardClick(helper.adapterPosition)
                }
                val tvLikeCount = helper.getView<TextView>(R.id.tvLikeCount)
                val ivVideoThumb = helper.getView<ImageView>(R.id.ivVideoThumb)
                GlideCenter.get().showCrossFadeImage(ivVideoThumb, bean.videoThumb)
                val ivAvatar = helper.getView<RoundImageView>(R.id.ivAvatar)
                GlideCenter.get().showCrossFadeImage(ivAvatar, bean.userAvatar)
                val tvUserName = helper.getView<TextView>(R.id.tvUserName)
                tvUserName.text = bean.userName

                val likeButton = helper.getView<LikeButton>(R.id.likeButton)
                likeButton.setOnLikeListener(object: OnLikeListener{
                    override fun liked(likeButton: LikeButton?) {
                        onVideoCardClickListener?.onLikeButtonClick(true)
                        likeCount++
                        tvLikeCount.text = likeCount.toString()
                    }

                    override fun unLiked(likeButton: LikeButton?) {
                        onVideoCardClickListener?.onLikeButtonClick(false)
                        likeCount--
                        tvLikeCount.text = likeCount.toString()
                    }
                })
            }
        }
    }

    interface OnVideoCardClickListener{
        fun onVideoCardClick(position:Int)
        fun onLikeButtonClick(isLike:Boolean)
    }
}