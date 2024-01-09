package com.umang.assignment.data.remote.image

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.umang.assignment.data.remote.image.imageapi.ImageApiService
import com.umang.assignment.data.remote.image.imagedatatype.ImageInfoData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepository @Inject constructor(private val apiService: ImageApiService) {

    fun getImages(): Flow<PagingData<ImageInfoData>> {
        return try {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { ImagePagingSource(apiService) }
            ).flow
        } catch (e: Exception) {
            throw e
        }
    }
}