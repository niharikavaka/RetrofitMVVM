package com.tutorial.retrofitmvvm.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tutorial.retrofitmvvm.R
import com.tutorial.retrofitmvvm.activities.MainActivity2
import com.tutorial.retrofitmvvm.activities.MealActivity
import com.tutorial.retrofitmvvm.databinding.FragmentFavouriteBinding
import com.tutorial.retrofitmvvm.databinding.FragmentMealBottomSheetBinding
import com.tutorial.retrofitmvvm.fragments.homeFragment
import com.tutorial.retrofitmvvm.viewModel.HomeViewModel

private const val MEAL_ID = "param1"



class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private var mealId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           mealId = it.getString(MEAL_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMealBottomSheetBinding.inflate(inflater,container,false)
        viewModel =(activity as MainActivity2).viewModel
      //  viewModel.getMealById(mealId)
        mealId?.let { viewModel.getMealById(it) }
        observeBottomSheetMeal()
        onBottomSheetDiologueClick()


        return binding.root
    }

    private fun onBottomSheetDiologueClick() {
        binding.bottomSheet.setOnClickListener {
            if(mealName !=null && mealThumb != null){
                val intent= Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(homeFragment.MEAL_ID,mealId)
                    putExtra(homeFragment.MEAL_NAME,mealName)
                    putExtra(homeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }
    private var mealName:String?=null
    private var mealThumb:String?=null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgBtmSheet)
            binding.tvBottomSheetCategory.text=meal.strCategory
            binding.tvBottomSheetArea.text=meal.strArea
            binding.tvBottomSheetMealName.text=meal.strMeal
            mealName=meal.strMeal
            mealThumb=meal.strMealThumb
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}