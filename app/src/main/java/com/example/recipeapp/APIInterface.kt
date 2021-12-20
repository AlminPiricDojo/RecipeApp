package com.example.recipeapp

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    // This function returns a Call (we have to use the await or awaitResponse function)
    @GET("recipes/")
    fun getAll(): Call<List<Recipe>>

    // This function returns a Response (we no longer need the await function)
    @GET("recipes/")
    suspend fun getAllResponse(): Response<List<Recipe>>

    @POST("recipes/")
    fun addRecipe(@Body recipeData: Recipe): Call<Recipe>
}