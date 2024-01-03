package com.tutorial.retrofitmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tutorial.retrofitmvvm.databinding.CategoryItemBinding
import com.tutorial.retrofitmvvm.databinding.MealItemBinding
import com.tutorial.retrofitmvvm.pojo.Category
import com.tutorial.retrofitmvvm.pojo.MealsByCategory

class CategoryMealAdapter():RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewHolder>() {
   lateinit var onItemClick:((MealsByCategory)->Unit)
    private var CategoryMealList=ArrayList<MealsByCategory>()
    fun setCategoryMealData(categoryMeal:ArrayList<MealsByCategory>){
        this.CategoryMealList=categoryMeal
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
             Glide.with(holder.itemView)
                 .load(CategoryMealList[position].strMealThumb)
                 .into(holder.binding.imgMeal)
            holder.binding.tvMealName.text=CategoryMealList[position].strMeal
           holder.itemView.setOnClickListener {
            onItemClick.invoke(CategoryMealList[position])
          }
    }


    override fun getItemCount(): Int {
          return CategoryMealList.size
    }

    class CategoryMealViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

}