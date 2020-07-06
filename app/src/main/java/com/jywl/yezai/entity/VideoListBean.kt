package com.jywl.yezai.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * created by Buzz
 * on 2020/7/6
 * email lmx2060918@126.com
 */

@Parcelize
data class VideoListBean (
    val userName:String,
    val userAvatar:String,
    val videoThumb:String,
    val videoPath:String,
    val likeCount:Int,
    val intro:String
) : Parcelable