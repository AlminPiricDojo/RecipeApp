package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @GET("recipes/")
    fun getAll(): Call<List<Recipe>>

    @POST("recipes/")
    fun addRecipe(@Body recipeData: Recipe): Call<Recipe>
}