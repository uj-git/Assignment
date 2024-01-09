package com.umang.assignment.data.remote.image.imageapi

import com.umang.assignment.data.remote.image.imagedatatype.ImageApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {

    @GET("w/api.php")
    suspend fun getFeaturedImages(
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "imageinfo",
        @Query("iiprop") iiprop: String = "timestamp|user|url",
        @Query("generator") generator: String = "categorymembers",
        @Query("gcmtype") gcmtype: String = "file",
        @Query("gcmtitle") gcmtitle: String = "Category:Featured_pictures_on_Wikimedia_Commons",
        @Query("format") format: String = "json",
        @Query("utf8") utf8: Boolean = true,
        @Query("gcmcontinue") gcmcontinue: String? = null
    ): Response<ImageApiResponse>

}