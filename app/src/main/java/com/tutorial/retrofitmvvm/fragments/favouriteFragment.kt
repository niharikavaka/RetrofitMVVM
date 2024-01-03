package com.tutorial.retrofitmvvm.fragments

import android.annotation.SuppressLint
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tutorial.retrofitmvvm.R
import com.tutorial.retrofitmvvm.activities.MainActivity2
import com.tutorial.retrofitmvvm.adapters.FavoritesItemAdapter
import com.tutorial.retrofitmvvm.databinding.FragmentFavouriteBinding
import com.tutorial.retrofitmvvm.databinding.FragmentHomeBinding
import com.tutorial.retrofitmvvm.pojo.Meal
import com.tutorial.retrofitmvvm.viewModel.HomeViewModel


class favouriteFragment : Fragment() {
    private lateinit var binding:FragmentFavouriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteAdapter:FavoritesItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentFavouriteBinding.inflate(inflater,container,false)
        viewModel =(activity as MainActivity2).viewModel
        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position=viewHolder.adapterPosition

                val deleteMeal=favoriteAdapter.differ.currentList[position]
                viewModel.deleteMeal(deleteMeal)
                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(deleteMeal)
                    }
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recViewFavorite)




        return binding.root
    }

    private fun prepareRecyclerView() {
        favoriteAdapter=FavoritesItemAdapter()
        binding.recViewFavorite.apply {
            layoutManager=GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
            adapter=favoriteAdapter
        }
    }

    private fun observeFavorites() {

        viewModel.observeFavoriteMealLiveData().observe(viewLifecycleOwner,object : Observer<List<Meal>>{
            @SuppressLint("SetTextI18n")
            override fun onChanged(value: List<Meal>) {
                   /*   value.forEach {
                          d("test",it.idMeal)
                      }*/
                 var count:Int?=null
                 var item:String?=null
                 if (value.size>1){
                    count=value.size
                     item="items"
                 }else{
                     count=value.size
                     item="item"
                 }
                  binding.tvFavoritesItemCount.text="Wishlist - $count $item"
                  favoriteAdapter.differ.submitList(value)

            }

        })
    }


}