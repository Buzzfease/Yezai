package com.jywl.yezai.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jywl.yezai.R
import com.jywl.yezai.utils.glide.GlideCenter

class PreviewSelectAdapter(private val mContext: Context, private val maxCount:Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mList = ArrayList<String>()
    private var mInflater: LayoutInflater? =null


    private var selectItemClickListener: MultiSelectItemClickListener? = null

    fun setSelectItemClickListener(selectItemClickListener: MultiSelectItemClickListener) {
        this.selectItemClickListener = selectItemClickListener
    }

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_IMAGE -> ImageHolder(mInflater!!.inflate(R.layout.layout_publish_holder, parent, false))
            ITEM_TYPE_ADD -> AddImageHolder(mInflater!!.inflate(R.layout.layout_publish_add, parent, false))
            else -> ImageHolder(mInflater!!.inflate(R.layout.layout_publish_holder, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageHolder -> bindItemImage(holder, position)
            is AddImageHolder -> bindItemAddImage(holder)
        }
    }

    private fun getDataCount():Int {
        return mList.size
    }

    override fun getItemCount(): Int {
        return mList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position + 1 == itemCount) {
            ITEM_TYPE_ADD
        } else {
            ITEM_TYPE_IMAGE
        }
    }

    fun getDataList():ArrayList<String> {
        return mList
    }

    fun addMoreItem(data: List<String>) {
        data.filter { getDataCount() < maxCount && !mList.contains(it) }.forEach { mList.add(it) }
        notifyDataSetChanged()
    }

    fun updateItem(list: List<String>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun updateData(list: List<String>){
        mList.clear()
        mList.addAll(list)
    }

    private fun bindItemAddImage(holder: AddImageHolder) {
        if (getDataCount() >= maxCount) {
            holder.ivAdd.visibility = View.GONE
        }else{
            holder.ivAdd.visibility = View.VISIBLE
        }
        holder.ivAdd.setOnClickListener {
            selectItemClickListener!!.addImageClick()
        }
    }

    private fun bindItemImage(holder: ImageHolder, position: Int) {
        GlideCenter.get().showImage(holder.ivAvatar, mList[position])
        holder.ivAvatar.setOnClickListener {
            selectItemClickListener!!.selectImageClick(position)
        }
        holder.ivAvatar.setOnLongClickListener {
            selectItemClickListener?.selectImageLongClick(position, holder)
            return@setOnLongClickListener true
        }
    }

    interface MultiSelectItemClickListener {
        fun addImageClick()

        fun selectImageClick(position: Int)

        fun selectImageLongClick(position: Int, holder: RecyclerView.ViewHolder)
    }

    internal inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivPic)
    }

    internal inner class AddImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAdd: ImageView = itemView.findViewById(R.id.ivAdd)
    }

    companion object {
        const val ITEM_TYPE_IMAGE = 0x00001
        const val ITEM_TYPE_ADD = 0x00002
    }
}