package com.tutorial.retrofitmvvm.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tutorial.retrofitmvvm.activities.CategoryMealsActivity
import com.tutorial.retrofitmvvm.activities.MainActivity2
import com.tutorial.retrofitmvvm.activities.MealActivity
import com.tutorial.retrofitmvvm.adapters.CategoriesAdapter
import com.tutorial.retrofitmvvm.adapters.MostPopularAdapter
import com.tutorial.retrofitmvvm.databinding.FragmentHomeBinding
import com.tutorial.retrofitmvvm.fragments.bottomsheet.MealBottomSheetFragment
import com.tutorial.retrofitmvvm.pojo.Category
import com.tutorial.retrofitmvvm.pojo.MealsByCategory
import com.tutorial.retrofitmvvm.pojo.Meal
import com.tutorial.retrofitmvvm.viewModel.HomeViewModel

class homeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID="com.tutorial.retrofitmvvm.fragments.idMeal"
        const val MEAL_NAME="com.tutorial.retrofitmvvm.fragments.nameMeal"
        const val MEAL_THUMB="com.tutorial.retrofitmvvm.fragments.thumbMeal"
        const val CATEGORY_NAME="com.tutorial.retrofitmvvm.fragments.categoryName"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //In homeMvvm we have  private lateinit var homeMvvm:HomeViewModel
        //homeMvvm= ViewModelProvider(this)[HomeViewModel::class.java]
        binding=FragmentHomeBinding.inflate(inflater,container,false)

        viewModel =(activity as MainActivity2).viewModel
        popularItemsAdapter= MostPopularAdapter()
        categoriesAdapter= CategoriesAdapter()

        viewModel.getRandomMeal()
        observerRandomMeal()
        viewModel.getPopularItems()
        observePopularItemsLiveData()
        viewModel.getCategoryItems()
        observeCategoryItemsLiveData()
        onRandomMealClick()
        preparePopularItemsRecyclerView()
        prepareCategoryItemsRecyclerView()
        onCategoryItemClick()
        onPopularItemClick()
        onPopularItemLongClick()



        return binding.root
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick={meal->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryItemClick() {
        categoriesAdapter.onItemClick={ category->

                val intent=Intent(activity,CategoryMealsActivity::class.java).apply {
                    putExtra(CATEGORY_NAME,category.strCategory)
                }

                startActivity(intent)

        }
    }
    private fun prepareCategoryItemsRecyclerView() {
        binding.recViewCategory.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observeCategoryItemsLiveData() {
        viewModel.observeCategoryItemsLiveData().observe(viewLifecycleOwner,object : Observer<List<Category>>{
            /*   override fun onChanged(t: List<Category>?) {
                   categoriesAdapter.setCategoryData(categoryList = t as ArrayList<Category>)
            }
*/
            override fun onChanged(value: List<Category>) {
                categoriesAdapter.setCategoryData(categoryList = value as ArrayList<Category>)
            }
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick={ meal->
            val intent = Intent(activity, MealActivity::class.java).apply {
                putExtra(MEAL_ID,meal.idMeal)
                putExtra(MEAL_NAME,meal.strMeal)
                putExtra(MEAL_THUMB,meal.strMealThumb)
            }

            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        //after convert to  lambda the below line change before that it looks like in observerRandomMeal() function. video 7
       viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,object : Observer<List<MealsByCategory>>{
           /*  override fun onChanged(t: List<MealsByCategory>?) {
               popularItemsAdapter.setMeals(mealsList = t as ArrayList<MealsByCategory>)
           }*/
           override fun onChanged(value: List<MealsByCategory>) {
               popularItemsAdapter.setMeals(mealsList = value as ArrayList<MealsByCategory>)
           }

       })

    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java).apply {
                putExtra(MEAL_ID,randomMeal.idMeal)
                putExtra(MEAL_NAME,randomMeal.strMeal)
                putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            }

            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(this@homeFragment)
                    .load(value!!.strMealThumb)
                    .into(binding.randomMealImg)
                this@homeFragment.randomMeal=value

            }


        })
    }


}

