package com.jywl.yezai.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PicWithExt(
    var attachPath:String,
    var attachExt:String,
    var attachTitle:String
):Parcelable

