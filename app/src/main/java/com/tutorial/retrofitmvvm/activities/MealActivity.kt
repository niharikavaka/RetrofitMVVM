package com.tutorial.retrofitmvvm.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tutorial.retrofitmvvm.R
import com.tutorial.retrofitmvvm.databinding.ActivityMealBinding
import com.tutorial.retrofitmvvm.db.MealDatabase
import com.tutorial.retrofitmvvm.fragments.homeFragment
import com.tutorial.retrofitmvvm.pojo.Meal
import com.tutorial.retrofitmvvm.viewModel.MealViewModel
import com.tutorial.retrofitmvvm.viewModel.MealViewModelFactory


class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private lateinit var mealMvvm:MealViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDataBase=MealDatabase.getInstance(this)
        val viewModelFactory= MealViewModelFactory(mealDataBase)
        mealMvvm=ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

   //  mealMvvm=ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationViews()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetail()
        onYoutubeImageClick()
       onFavoriteClick()



    }

    private fun onFavoriteClick() {
            binding.btnAddToFavorites.setOnClickListener {
               // mealMvvm.insertMeal(mealToAdd!!)

                mealToAdd?.let {
                    mealMvvm.insertMeal(it)
                    Toast.makeText(this,"Added Meal",Toast.LENGTH_SHORT).show()
                }


            }

    }

    private fun onYoutubeImageClick() {
       binding.youtubeImg.setOnClickListener {
           val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
           startActivity(intent)
       }
    }

    private fun onResponseCase() {
        binding.progressBar.visibility=View.INVISIBLE
        binding.tvArea.visibility=View.VISIBLE
        binding.tvCategory.visibility=View.VISIBLE
        binding.tvInstructions.visibility=View.VISIBLE
        binding.btnAddToFavorites.visibility=View.VISIBLE
        binding.youtubeImg.visibility=View.VISIBLE

    }

    private fun loadingCase() {
        binding.progressBar.visibility=View.VISIBLE
        binding.tvArea.visibility=View.INVISIBLE
        binding.tvCategory.visibility=View.INVISIBLE
        binding.tvInstructions.visibility=View.INVISIBLE
        binding.btnAddToFavorites.visibility=View.INVISIBLE
        binding.youtubeImg.visibility=View.INVISIBLE
    }
    private var mealToAdd:Meal?=null
    private fun observeMealDetail() {
          mealMvvm.observerMealDetailLiveData().observe(this,object : Observer<Meal>{

              override fun onChanged(value: Meal) {
                  val meal=value
                  mealToAdd=meal
                  onResponseCase()
                  binding.tvCategory.text="Category : ${meal.strCategory}"
                  binding.tvArea.text="Area : ${meal.strArea}"
                  binding.tvInstructionsSteps.text=meal.strInstructions
                  youtubeLink= meal.strYoutube.toString()
              }

          })
    }

    private fun setInformationViews() {
        //this or applicationContext
        Glide.with(this)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
       // val intent=intent   *not necessary*
        mealId= intent.getStringExtra(homeFragment.MEAL_ID)!!
        mealName= intent.getStringExtra(homeFragment.MEAL_NAME)!!
        mealThumb= intent.getStringExtra(homeFragment.MEAL_THUMB)!!
    }
}