package com.example.android.justjava.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.justjava.R;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<Integer> mQuantities = new ArrayList<>();
    private ArrayList<Integer> mPrices = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mImageNames,
                               ArrayList<Integer> mImages,
                               ArrayList<Integer> mQuantities,
                               ArrayList<Integer> mPrices,
                               Context mContext) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mQuantities = mQuantities;
        this.mPrices = mPrices;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.pizzaName.setText(mImageNames.get(position));
        holder.pizzaQuantity.setText(mQuantities.get(position).toString());
        holder.pizzPrice.setText(mPrices.get(position).toString());

        holder.plusPizzaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentValueAtPosition = mQuantities.get(position);
                mQuantities.set(position, ++currentValueAtPosition);
                notifyItemChanged(position);
            }
        });

        holder.minusPizzaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentValueAtPosition = mQuantities.get(position);
                mQuantities.set(position, --currentValueAtPosition);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView pizzaName;
        TextView pizzaQuantity;
        TextView pizzPrice;
        Button minusPizzaBtn;
        Button plusPizzaBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            pizzaName = itemView.findViewById(R.id.pizzaName);
            pizzaQuantity = itemView.findViewById(R.id.pizzaQuantity);
            pizzPrice = itemView.findViewById(R.id.pizzaPrice);
            minusPizzaBtn = itemView.findViewById(R.id.minusPizzaBtn);
            plusPizzaBtn = itemView.findViewById(R.id.plusPizzaBtn);
        }
    }
}
