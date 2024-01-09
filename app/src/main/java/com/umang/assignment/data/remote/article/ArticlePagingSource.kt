package com.umang.assignment.data.remote.article

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.umang.assignment.data.remote.article.articleapi.ArticleApiService
import com.umang.assignment.data.remote.article.articledatatype.Page
import retrofit2.HttpException


class ArticlePagingSource(private val imageService: ArticleApiService) : PagingSource<String, Page>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Page> {
        try {
            val response = imageService.getRandomArticles(grnContinue = params.key)
            val pages = response.query.pages.values.toList()

            val nextPageKey = response.continueData?.grncontinue

            if (pages.isEmpty()) {
                // Handle the case where the loaded data is empty
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            // Ensure the requested index is within bounds
            val validIndex = params.key?.toIntOrNull() ?: 0
            if (validIndex >= pages.size) {
                // Return an empty page to avoid index out of bounds
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            return LoadResult.Page(
                data = pages,
                prevKey = null,
                nextKey = nextPageKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Page>): String? {
        if (state.isEmpty()) {
            return null
        }
        return System.currentTimeMillis().toString()
    }
}


//class ArticlePagingSource(private val imageService: ArticleApiService) : PagingSource<String, Page>() {
//
//        override suspend fun load(params: LoadParams<String>): LoadResult<String, Page> {
//            try {
//                val response = imageService.getRandomArticles(grnContinue = params.key)
//                val pages = response.query.pages.values.toList()
//
//                val nextPageKey = response.continueData?.grncontinue
//
//                return LoadResult.Page(
//                    data = pages,
//                    prevKey = null,
//                    nextKey = nextPageKey
//                )
//            } catch (e: Exception) {
//                return LoadResult.Error(e)
//            }catch (e: HttpException) {
//                return LoadResult.Error(e)
//            }
//        }
//
//    override fun getRefreshKey(state: PagingState<String, Page>): String? {
//        if (state.isEmpty()) {
//            return null
//        }
//        return System.currentTimeMillis().toString()
//    }
//}

