package com.atuvwapps.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.atuvwapps.bakingapp.R;
import com.atuvwapps.bakingapp.data.Step;
import java.util.List;

public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Step> steps;
    private ItemClickListener itemClickListener;

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView shortDescTextView;
        public ItemClickListener itemClickListener;

        ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.itemClickListener = itemClickListener;
            shortDescTextView = itemView.findViewById(R.id.shortDesc_tv);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }

    // data is passed into the constructor
    public StepRecyclerViewAdapter(Context context, List<Step> steps, ItemClickListener itemClickListener) {
        this.context = context;
        this.steps = steps;
        this.itemClickListener = itemClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step_recycler_row, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step currentStep = steps.get(position);
        int stepCount = currentStep.getId() + 1;
        holder.shortDescTextView.setText(String.valueOf(stepCount) + ") "
                + currentStep.getShortDescription());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return steps.size();
    }

    public interface ItemClickListener {
        void onClick(int position);
    }
}
