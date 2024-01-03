package com.tutorial.retrofitmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tutorial.retrofitmvvm.pojo.Meal


@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>

}

