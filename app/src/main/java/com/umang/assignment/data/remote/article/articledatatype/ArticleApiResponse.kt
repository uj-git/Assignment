package com.umang.assignment.data.remote.article.articledatatype

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArticleApiResponse(
    val continueData: ContinueData,
    val query: Query
)

data class ContinueData(
    val imcontinue: String,
    val grncontinue: String,
    val continueValue: String
)

data class Query(
    val pages: Map<String, Page>
)

@Parcelize
data class Page(
    val pageid: Int,
    val ns: Int,
    val title: String,
    val revisions: List<Revision>
) : Parcelable

@Parcelize
data class Revision(
    val contentformat: String,
    val contentmodel: String,
    @SerializedName("*")
    val content: String
) : Parcelable
