package com.atuvwapps.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.atuvwapps.bakingapp.R;
import com.atuvwapps.bakingapp.data.Recipe;
import com.bumptech.glide.Glide;
import java.util.List;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Recipe> recipes;
    private ItemClickListener itemClickListener;

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;
        public ImageView myImageView;
        public ItemClickListener itemClickListener;

        ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.itemClickListener = itemClickListener;
            myTextView = itemView.findViewById(R.id.recipe_name_tv);
            myImageView = itemView.findViewById(R.id.recipe_image_iv);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }

    // data is passed into the constructor
    public RecipeRecyclerViewAdapter(Context context, List<Recipe> recipes, ItemClickListener itemClickListener) {
        this.context = context;
        this.recipes = recipes;
        this.itemClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_recycler_row, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        holder.myTextView.setText(currentRecipe.getName());
        if(currentRecipe.getImage().isEmpty()){
            Glide.with(context).load(R.mipmap.baking).into(holder.myImageView);
        }else {
            Glide.with(context).load(currentRecipe.getImage()).placeholder(R.mipmap.baking)
                    .error(R.mipmap.baking).into(holder.myImageView);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public interface ItemClickListener {
        void onClick(int position);
    }
}