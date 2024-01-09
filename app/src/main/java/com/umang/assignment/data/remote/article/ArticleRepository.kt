package com.umang.assignment.data.remote.article

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.umang.assignment.data.remote.article.articleapi.ArticleApiService
import com.umang.assignment.data.remote.article.articledatatype.Page
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val apiService: ArticleApiService) {

    fun getRandomArticles(): Flow<PagingData<Page>> {
        return try {
            Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false),
                pagingSourceFactory = { ArticlePagingSource(apiService) }
            ).flow

        } catch (e: Exception) {
            throw e
        }
    }
}