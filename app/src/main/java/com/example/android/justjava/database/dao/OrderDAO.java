package com.example.android.justjava.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.justjava.database.entities.OrderEntity;

import java.util.List;

@Dao
public interface OrderDAO {

    @Query("SELECT * FROM orders")
    public List<OrderEntity> getAllOrders();

    @Query("SELECT * FROM orders WHERE id = :id")
    public OrderEntity getOrderById (Long id);

    @Insert
    public void insert(OrderEntity... userEntities);

    @Update
    public void update(OrderEntity... userEntities);

    @Query("DELETE FROM orders")
    public void deleteAll();

    @Query("DELETE FROM orders WHERE id = :id")
    public void deleteOrderById(Long id);

}
