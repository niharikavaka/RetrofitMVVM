package com.tutorial.retrofitmvvm.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tutorial.retrofitmvvm.R
import com.tutorial.retrofitmvvm.activities.CategoryMealsActivity
import com.tutorial.retrofitmvvm.activities.MainActivity2
import com.tutorial.retrofitmvvm.adapters.CategoriesAdapter
import com.tutorial.retrofitmvvm.adapters.FavoritesItemAdapter
import com.tutorial.retrofitmvvm.databinding.FragmentCategoriesBinding
import com.tutorial.retrofitmvvm.databinding.FragmentFavouriteBinding
import com.tutorial.retrofitmvvm.pojo.Category
import com.tutorial.retrofitmvvm.viewModel.HomeViewModel

class categoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentCategoriesBinding.inflate(inflater,container,false)
        viewModel =(activity as MainActivity2).viewModel

        prepareRecyclerView()
        observeCategories()
        onCategoryItemClick()




        return binding.root

    }
    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick={ category->

            val intent= Intent(activity, CategoryMealsActivity::class.java).apply {
                putExtra(homeFragment.CATEGORY_NAME,category.strCategory)
            }

            startActivity(intent)

        }
    }

    private fun observeCategories() {
        viewModel.observeCategoryItemsLiveData().observe(viewLifecycleOwner,object : Observer<List<Category>>{
            override fun onChanged(value: List<Category>) {
                categoryAdapter.setCategoryData(value as ArrayList<Category>)
            }

        })
    }

    private fun prepareRecyclerView() {
        categoryAdapter=CategoriesAdapter()
        binding.recViewCategoryFull.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter

        }
    }


}