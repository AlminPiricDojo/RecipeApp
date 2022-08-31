package com.example.recipeapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeAppModule {
    @Provides
    @Singleton
    fun provideAPIInterface(): APIInterface {
        return Retrofit.Builder()
            .baseUrl("https://apidojo.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }
}