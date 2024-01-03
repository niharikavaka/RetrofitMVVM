package com.tutorial.retrofitmvvm.retrofit

import com.tutorial.retrofitmvvm.pojo.CategoryList
import com.tutorial.retrofitmvvm.pojo.MealByCategoryList
import com.tutorial.retrofitmvvm.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i")id:String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")categroryName:String):Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealByCategory(@Query("c")categroryName:String):Call<MealByCategoryList>
}