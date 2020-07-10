package com.jywl.yezai.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * created by Buzz
 * on 2020/7/10
 * email lmx2060918@126.com
 */
@Parcelize
data class ConcernBean(
    var id:Int,
    var userAvatar:String,
    var userName:String,
    var isConcern:Boolean
): Parcelable