package com.umang.assignment.data.remote.image.imagedatatype

import com.google.gson.annotations.SerializedName

data class ImageApiResponse(
    @SerializedName("batchcomplete")
    val batchComplete: String,
    @SerializedName("continue")
    val continueData: ContinueData,
    @SerializedName("query")
    val query: QueryData
)

data class ContinueData(
    @SerializedName("gcmcontinue")
    val gcmContinue: String?,
    @SerializedName("continue")
    val continueParam: String?
)

data class QueryData(
    @SerializedName("pages")
    val pages: Map<String, ImagePageData>
)

data class ImagePageData(
    @SerializedName("pageid")
    val pageId: Int,
    @SerializedName("ns")
    val ns: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("imagerepository")
    val imageRepository: String,
    @SerializedName("imageinfo")
    val imageInfo: List<ImageInfoData>
)

data class ImageInfoData(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("url")
    val imageUrl: String,
    @SerializedName("descriptionurl")
    val descriptionUrl: String,
    @SerializedName("descriptionshorturl")
    val descriptionShortUrl: String
)
