package com.jywl.yezai.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * created by Buzz
 * on 2020/7/7
 * email lmx2060918@126.com
 */
@Parcelize
data class UserBean(
    val userAvatar:String,
    val userName:String
):Parcelable