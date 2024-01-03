package com.tutorial.retrofitmvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tutorial.retrofitmvvm.db.MealDatabase

class MealViewModelFactory(private val mealDatabase: MealDatabase):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T


    }
}
