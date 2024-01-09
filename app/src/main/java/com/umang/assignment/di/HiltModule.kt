package com.umang.assignment.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.umang.assignment.BASE_URL_Article
import com.umang.assignment.BASE_URL_Image
import com.umang.assignment.data.remote.image.ImageRepository
import com.umang.assignment.data.local.UserDao
import com.umang.assignment.data.local.UserDatabase
import com.umang.assignment.data.remote.article.articleapi.ArticleApiService
import com.umang.assignment.data.remote.article.ArticleRepository
import com.umang.assignment.data.remote.image.imageapi.ImageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Provides
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): UserDatabase {
        return UserDatabase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(appDatabase: UserDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    @Named("image")
    fun getImageRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL_Image)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getImageApi(@Named("image") retrofit: Retrofit) : ImageApiService {
        return retrofit.create(ImageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(apiService: ImageApiService): ImageRepository {
        return ImageRepository(apiService)
    }

    @Singleton
    @Provides
    @Named("article")
    fun getArticleRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL_Article)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getArticleApi(@Named("article") retrofit: Retrofit) : ArticleApiService {
        return retrofit.create(ArticleApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideArticleRepository(apiService: ArticleApiService) : ArticleRepository {
        return ArticleRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("Your_Preferences_Name", Context.MODE_PRIVATE)
    }

}
