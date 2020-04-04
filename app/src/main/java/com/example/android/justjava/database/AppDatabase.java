package com.example.android.justjava.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.android.justjava.database.dao.OrderDAO;
import com.example.android.justjava.database.entities.OrderEntity;

@Database(entities = {OrderEntity.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {
    public abstract OrderDAO getOrderDAO();
}
