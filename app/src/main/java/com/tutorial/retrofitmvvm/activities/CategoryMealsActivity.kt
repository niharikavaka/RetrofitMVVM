package com.tutorial.retrofitmvvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tutorial.retrofitmvvm.R
import com.tutorial.retrofitmvvm.adapters.CategoryMealAdapter
import com.tutorial.retrofitmvvm.databinding.ActivityCategoryMealsBinding
import com.tutorial.retrofitmvvm.fragments.homeFragment
import com.tutorial.retrofitmvvm.pojo.MealsByCategory
import com.tutorial.retrofitmvvm.viewModel.CategoryViewModel
import com.tutorial.retrofitmvvm.viewModel.MealViewModel

class CategoryMealsActivity : AppCompatActivity() {
   private lateinit var binding: ActivityCategoryMealsBinding
   private lateinit var categoryName:String
   private lateinit var categoryViewModel:CategoryViewModel
   private lateinit var categoryMealAdapter:CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getInformationFromIntent()
        categoryViewModel= ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryViewModel.getCategoryItems(categoryName)
        observeMealByCategoryLiveData()
        prepareCategoryMealRecyclerView()

       onCategoryItemClick()
    }

    private fun onCategoryItemClick() {
        categoryMealAdapter.onItemClick={ meal->
            val intent = Intent(this, MealActivity::class.java).apply {
                putExtra(homeFragment.MEAL_ID,meal.idMeal)
                putExtra(homeFragment.MEAL_NAME,meal.strMeal)
                putExtra(homeFragment.MEAL_THUMB,meal.strMealThumb)
            }

            startActivity(intent)
        }
    }

    private fun prepareCategoryMealRecyclerView() {
        categoryMealAdapter= CategoryMealAdapter()
        binding.recViewCategoryMeal.apply {
            //this or context we can keep
            layoutManager=GridLayoutManager(this@CategoryMealsActivity,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealAdapter
        }

    }

    private fun observeMealByCategoryLiveData() {
        categoryViewModel.observeMealByCategoryLiveData().observe(this,object : Observer<List<MealsByCategory>>{
            override fun onChanged(value: List<MealsByCategory>) {
                binding.tvCategoryCount.text= "$categoryName : ${value!!.size}"
                categoryMealAdapter.setCategoryMealData(value as ArrayList<MealsByCategory>)
                /* t!!.forEach {
                            d("test",it.strMeal)
                       }  or
                       */
            }


        })
    }


    private fun getInformationFromIntent() {
        categoryName=intent.getStringExtra(homeFragment.CATEGORY_NAME)!!

    }
}