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
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.android.justjava.R;
import com.example.android.justjava.database.AppDatabase;
import com.example.android.justjava.database.dao.OrderDAO;
import com.example.android.justjava.database.entities.OrderEntity;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<OrderEntity> orderEntityList;

    private Context mContext;
    AppDatabase database;
    OrderDAO orderDAO;

    public RecyclerViewAdapter(List<OrderEntity> orderEntities, Context mContext) {
        orderEntityList = orderEntities;
        this.mContext = mContext;
        database = Room.databaseBuilder(mContext, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        orderDAO = database.getOrderDAO();
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
                //.load(mImages.get(position))
                .load(orderEntityList.get(position).getImageId())
                .into(holder.image);

        holder.pizzaName.setText(orderEntityList.get(position).getFoodName());
        holder.pizzaQuantity.setText(String.valueOf(orderEntityList.get(position).getQuantity()));
        holder.pizzPrice.setText(String.valueOf(orderEntityList.get(position).getPrice()));

        holder.plusPizzaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderEntity currentEntity = orderEntityList.get(position);
                int currentValueAtPosition = currentEntity.getQuantity();
                currentEntity.setQuantity(++currentValueAtPosition);
                orderDAO.update(currentEntity);
                notifyItemChanged(position);
            }
        });

        holder.minusPizzaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderEntity currentEntity = orderEntityList.get(position);
                int currentValueAtPosition = currentEntity.getQuantity();

                if (currentValueAtPosition > 0) {
                    currentEntity.setQuantity(--currentValueAtPosition);
                    orderDAO.update(currentEntity);
                    notifyItemChanged(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderEntityList.size();
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
