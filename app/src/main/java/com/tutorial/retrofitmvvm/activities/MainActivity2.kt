package com.tutorial.retrofitmvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tutorial.retrofitmvvm.R
import com.tutorial.retrofitmvvm.db.MealDatabase
import com.tutorial.retrofitmvvm.fragments.categoriesFragment
import com.tutorial.retrofitmvvm.fragments.favouriteFragment
import com.tutorial.retrofitmvvm.fragments.homeFragment
import com.tutorial.retrofitmvvm.viewModel.HomeViewModel
import com.tutorial.retrofitmvvm.viewModel.HomeViewModelFactory
import com.tutorial.retrofitmvvm.viewModel.MealViewModel
import com.tutorial.retrofitmvvm.viewModel.MealViewModelFactory

class  MainActivity2 : AppCompatActivity() {

    val viewModel:HomeViewModel by lazy{
        val mealDataBase= MealDatabase.getInstance(this)
        val homeViewModelFactory= HomeViewModelFactory(mealDataBase)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val bottomNav:BottomNavigationView=findViewById(R.id.bottomNav)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,homeFragment()).commit()


        /*  val navController= Navigation.findNavController(this,R.id.fragmentContainer)
        NavigationUI.setupWithNavController(bottomNav,navController)*/

        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,homeFragment()).commit()
                    true
                }
                R.id.favouriteFragment->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,favouriteFragment()).commit()
                true
                }
                R.id.categoriesFragment->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,categoriesFragment()).commit()
                true
                }

                else -> {
                    TODO()
                }
            }

        }


    }





}