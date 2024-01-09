package com.umang.assignment.data.remote.article.articleapi

import com.umang.assignment.data.remote.article.articledatatype.ArticleApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {

    @GET("/w/api.php")
    suspend fun getRandomArticles(
        @Query("format") format: String = "json",
        @Query("action") action: String = "query",
        @Query("generator") generator: String = "random",
        @Query("grnnamespace") grnNamespace: Int = 0,
        @Query("prop") prop: String = "revisions|images",
        @Query("rvprop") rvProp: String = "content",
        @Query("grnlimit") grnLimit: Int = 10,
        @Query("grncontinue") grnContinue: String? = null
    ): ArticleApiResponse

}