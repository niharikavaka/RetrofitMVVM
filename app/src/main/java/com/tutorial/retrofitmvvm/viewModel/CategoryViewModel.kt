package com.tutorial.retrofitmvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tutorial.retrofitmvvm.pojo.MealByCategoryList
import com.tutorial.retrofitmvvm.pojo.MealsByCategory
import com.tutorial.retrofitmvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel():ViewModel() {
    private var mealByCategoryLiveData=MutableLiveData<List<MealsByCategory>>()

    fun getCategoryItems(categoryName:String){
        RetrofitInstance.api.getPopularItems(categoryName).enqueue(object :
            Callback<MealByCategoryList> {
            override fun onResponse(call: Call<MealByCategoryList>, response: Response<MealByCategoryList>) {
                if(response.body()!=null){
                   mealByCategoryLiveData.value=response.body()!!.meals

                }
            }

            override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }


        })
    }

    fun observeMealByCategoryLiveData(): LiveData<List<MealsByCategory>> {
        return mealByCategoryLiveData
    }
}