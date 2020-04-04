package com.example.android.justjava.database.dao;


import androidx.room.Dao;
import androidx.room.Query;

import com.example.android.justjava.database.entities.OrderEntity;

@Dao
public interface OrderDAO {
    @Query("SELECT * FROM orders WHERE id = :id")
    public OrderEntity getOrderById (Long id);
}
