package com.example.recipeapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // We use the SingletonComponenet here to keep our dependencies alive throughout the lifecycle of our application
object RecipeAppModule {
    @Provides // Tells Dagger-Hilt that this function provides a dependency
    @Singleton // Sets the scope of our dependency, creating only one instance throughout the lifetime of our application
    fun provideAPIInterface(): APIInterface {
        return Retrofit.Builder()
            .baseUrl("https://apidojo.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }
}