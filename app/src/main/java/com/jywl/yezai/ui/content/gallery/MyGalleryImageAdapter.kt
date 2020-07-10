package com.jywl.yezai.ui.content.gallery

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jywl.yezai.R
import com.jywl.yezai.entity.MultiTypeItem
import com.jywl.yezai.ui.widget.RoundImageView
import com.jywl.yezai.ui.widget.WidthSquareImageView
import com.jywl.yezai.utils.glide.GlideCenter

/**
 * created by Buzz
 * on 2020/7/10
 * email lmx2060918@126.com
 */
class MyGalleryImageAdapter : BaseMultiItemQuickAdapter<MultiTypeItem<Any>, BaseViewHolder>(null) {

    companion object{
        const val ITEM_GALLERY_IMAGE = 0
    }

    init {
        addItemType(ITEM_GALLERY_IMAGE, R.layout.layout_my_gallery_image_holder)
    }

    override fun convert(helper: BaseViewHolder, item: MultiTypeItem<Any>?) {
        val url = item?.getData() as String
        when(item.itemType){
            ITEM_GALLERY_IMAGE ->{
                val imageView = helper.getView<WidthSquareImageView>(R.id.ivPic)
                GlideCenter.get().showCrossFadeImage(imageView, url)

                helper.addOnClickListener(R.id.ivPic, R.id.ivClose)
            }
        }
    }
}