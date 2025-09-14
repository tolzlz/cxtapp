package com.cxtapp.network
import ServiceSiteProtoKt
import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import com.cxtapp.common.json.UriSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import serviceSiteProto

/***
 * json解析方式
 */
@Serializable
@Parcelize
class ServiceSite(
    @SerialName("domain")
    @Serializable(with = UriSerializer::class)
    var uri: Uri,
    var languageCode: String = "") : Parcelable {
}

// --- 转换扩展函数 ---
fun ServiceSite.toProto(): ServiceSiteProtoOuterClass.ServiceSiteProto {
    return serviceSiteProto {
        uri = this@toProto.uri.toString()
        languageCode = this@toProto.languageCode
    }
}

fun ServiceSiteProtoOuterClass.ServiceSiteProto.toDomain(): ServiceSite {
    return ServiceSite(
        uri = this.uri.toUri(),
        languageCode = this.languageCode
    )
}