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
    var userName:String,
    var userAvatar:String,
    var videoThumb:String,
    var videoPath:String,
    var likeCount:Int,
    var intro:String
) : Parcelable