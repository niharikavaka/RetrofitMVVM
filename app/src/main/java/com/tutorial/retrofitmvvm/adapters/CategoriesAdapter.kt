package com.tutorial.retrofitmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tutorial.retrofitmvvm.databinding.CategoryItemBinding
import com.tutorial.retrofitmvvm.pojo.Category
import com.tutorial.retrofitmvvm.pojo.MealsByCategory

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryItemsViewHolder>() {
    lateinit var onItemClick:((Category)->Unit)
    private var CategoryItem=ArrayList<Category>()

    fun setCategoryData(categoryList:ArrayList<Category>){
        this.CategoryItem=categoryList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemsViewHolder {
        return CategoryItemsViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryItemsViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(CategoryItem[position].strCategoryThumb)
           .into(holder.binding.imgCategory)
       holder.binding.tvCategoryName.text=CategoryItem[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick.invoke(CategoryItem[position])
        }
    }

    override fun getItemCount(): Int {
       return CategoryItem.size
    }


    class CategoryItemsViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)


}