package com.example.recipeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemRowBinding

class RVAdapter(private var recipes: List<Recipe>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.binding.apply {
            tvTitle.text = recipe.title
            tvAuthor.text = recipe.author
            tvIngredients.text = recipe.ingredients
            tvInstructions.text = recipe.instructions
        }
    }

    override fun getItemCount() = recipes.size

    fun update(recipes: List<Recipe>){
        this.recipes = recipes
        notifyDataSetChanged()
    }
}