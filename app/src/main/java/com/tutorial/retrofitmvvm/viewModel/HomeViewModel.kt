package com.tutorial.retrofitmvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.retrofitmvvm.db.MealDatabase
import com.tutorial.retrofitmvvm.pojo.*
import com.tutorial.retrofitmvvm.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase):ViewModel(){
   // lateinit var ske:MutableLiveData<Meal>
    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoryItemsLiveData=MutableLiveData<List<Category>>()
    private var favoriteMealLiveData=mealDatabase.mealDao().getAllMeals()
    private var bottomsheetLiveData=MutableLiveData<Meal>()


    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               val meal=response.body()?.meals?.first()
                meal?.let {
                    bottomsheetLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }


    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val randomMeal: Meal =response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal

                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })

    }

        fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }


    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealByCategoryList>{
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if(response.body()!=null){
                  popularItemsLiveData.value=response.body()!!.meals

                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }


        })
    }

    fun observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun getCategoryItems(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body()!=null){
                    categoryItemsLiveData.value=response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }
    fun observeCategoryItemsLiveData():LiveData<List<Category>>{
        return categoryItemsLiveData
    }

   fun observeFavoriteMealLiveData():LiveData<List<Meal>>{
       return favoriteMealLiveData
   }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
  fun observeBottomSheetLiveData():LiveData<Meal> = bottomsheetLiveData

}