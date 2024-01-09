package com.umang.assignment.data.remote.image

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.umang.assignment.data.remote.image.imageapi.ImageApiService
import com.umang.assignment.data.remote.image.imagedatatype.ImageInfoData
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(private val apiService: ImageApiService) : PagingSource<String, ImageInfoData>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ImageInfoData> {
        try {
            val response = apiService.getFeaturedImages(gcmcontinue = params.key)
            if (response.isSuccessful) {
                val result = response.body()
                val data = result?.query?.pages?.values?.flatMap { it.imageInfo } ?: emptyList()
                val nextKey = result?.continueData?.gcmContinue

                if (data.isEmpty()) {
                    // Handle the case where the loaded data is empty
                    return LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }

                // Ensure the requested index is within bounds
                val validIndex = params.key?.toIntOrNull() ?: 0
                if (validIndex >= data.size) {
                    // Return an empty page to avoid index out of bounds
                    return LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }

                return LoadResult.Page(
                    data = data,
                    prevKey = null,
                    nextKey = nextKey
                )
            } else {
                return LoadResult.Error(IOException("Failed to load"))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, ImageInfoData>): String? {
        if (state.isEmpty()) {
            return null
        }
        return state.pages.firstOrNull()?.data?.firstOrNull()?.timestamp
    }
}


//class ImagePagingSource(private val apiService: ImageApiService) : PagingSource<String, ImageInfoData>() {
//
//    override suspend fun load(params: LoadParams<String>): LoadResult<String, ImageInfoData> {
//        try {
//            val response = apiService.getFeaturedImages(gcmcontinue = params.key)
//            if (response.isSuccessful) {
//                val result = response.body()
//                val data = result?.query?.pages?.values?.flatMap { it.imageInfo } ?: emptyList()
//                val nextKey = result?.continueData?.gcmContinue
//
//                return LoadResult.Page(
//                    data = data,
//                    prevKey = null,
//                    nextKey = nextKey
//                )
//            } else {
//                return LoadResult.Error(IOException("Failed to load"))
//            }
//        } catch (e: IOException) {
//            return LoadResult.Error(e)
//        } catch (e: HttpException) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<String, ImageInfoData>): String? {
//        if (state.isEmpty()) {
//            return null
//        }
//        return state.pages.firstOrNull()?.data?.firstOrNull()?.timestamp
//    }
//}