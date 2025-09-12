package com.cxtapp.network
import android.net.Uri
import android.os.Parcelable
import com.cxtapp.common.json.UriSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
@Parcelize
class ServiceSite(@SerialName("domain") @Serializable(with = UriSerializer::class) var uri: Uri,
                  var languageCode: String = "") : Parcelable {
}