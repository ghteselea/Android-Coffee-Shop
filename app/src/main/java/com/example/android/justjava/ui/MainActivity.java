package com.example.android.justjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjava.R;
import com.example.android.justjava.adapters.RecyclerViewAdapter;
import com.example.android.justjava.database.AppDatabase;
import com.example.android.justjava.database.dao.OrderDAO;
import com.example.android.justjava.database.entities.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    Properties
    TextView totalTextView;
    Button submitOrderBtn;
    Button sendOrderBtn;

    private int totalPrice = 0;
    private String finalText = "";

    private List<OrderEntity> orderEntityList;

    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    private static final String TAG = "MainActivity";

    AppDatabase database;
    OrderDAO orderDAO;

//    Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
        addListeners();
        initializeDatabase();
        initAdapter();
    }

//    Custom methods
    private void setup() {
        totalTextView = findViewById(R.id.price_value);
        submitOrderBtn = findViewById(R.id.submitBtn);
        sendOrderBtn= findViewById(R.id.sendEmailBtn);
    }

    void initializeDatabase() {
        database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        orderDAO = database.getOrderDAO();
    }

    private void addListeners() {
        submitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<OrderEntity> orderEntities = orderDAO.getAllOrders();

                finalText = "";
                totalPrice = 0;

                for (int i = 0; i <= orderEntities.size()-1; i++) {

                    OrderEntity currentOrder = orderEntities.get(i);

                    if(currentOrder.getQuantity() > 0) {
                        finalText = finalText + currentOrder.getFoodName() + ": "
                                + String.valueOf(currentOrder.getQuantity()) + " - Pret: "
                                + String.valueOf(currentOrder.getQuantity() * currentOrder.getPrice()) + " Ron" + "\n";

                        totalPrice = totalPrice + currentOrder.getQuantity() * currentOrder.getPrice();
                    }

                }

                finalText = finalText + "\n " + String.valueOf(totalPrice) + " Ron.";
                totalTextView.setText(finalText);

                if(totalPrice > 0 ) {
                    sendOrderBtn.setVisibility(View.VISIBLE);
                    sendOrderBtn.setEnabled(true);
                }
            }
        });

        sendOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert("Esti sigur ca vrei sa finalizezi comanda?");
            }
        });
    }

    private void sendEmail() {

        String email = "gteselea@yahoo.com";


        Intent emailIntent = new Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + email));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Comanda mea");
        emailIntent.putExtra(Intent.EXTRA_TEXT, finalText);
        startActivity(Intent.createChooser(emailIntent, "Alege ce vrei sa folosesti"));
    }

    private void showAlert(String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage(title);
        alert.setCancelable(true);

        alert.setPositiveButton(
                "DA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        sendEmail();
                    }
                });

        alert.setNegativeButton(
                "NU",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Atunci esti un bulangiu!", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void addOrderToDatabase(String foodName, int quantity, int price, int imageId) {
        OrderEntity product = new OrderEntity(foodName, quantity, price, imageId);
        orderDAO.insert(product);
    }

    private void repopulateDatabase() {
        orderDAO.deleteAll();
        addOrderToDatabase("Pizza Capriciosa", 0, 25, R.drawable.capriciosa);
        addOrderToDatabase("Pizza Morgana", 0, 25, R.drawable.morgana);
        addOrderToDatabase("Pizza De la Baicoi", 0, 25, R.drawable.de_la_baicoi);
        addOrderToDatabase("Pizza Hawaiana", 0, 25, R.drawable.hawaiana);
        addOrderToDatabase("Pizza Quatro Scarbosenii", 0, 25, R.drawable.quattro_scarbosenii);
        addOrderToDatabase("Pizza Vegetariana", 0, 25, R.drawable.vegetariana);
        addOrderToDatabase("Pizza Capriciosa", 0, 25, R.drawable.capriciosa);
        addOrderToDatabase("Pizza Morgana", 0, 25, R.drawable.morgana);
        addOrderToDatabase("Pizza De la Baicoi", 0, 25, R.drawable.de_la_baicoi);
        addOrderToDatabase("Pizza Hawaiana", 0, 25, R.drawable.hawaiana);
        addOrderToDatabase("Pizza Quatro Scarbosenii", 0, 25, R.drawable.quattro_scarbosenii);
        addOrderToDatabase("Pizza Vegetariana", 0, 25, R.drawable.vegetariana);
        addOrderToDatabase("Pizza Capriciosa", 0, 25, R.drawable.capriciosa);
        addOrderToDatabase("Pizza Morgana", 0, 25, R.drawable.morgana);
        addOrderToDatabase("Pizza De la Baicoi", 0, 25, R.drawable.de_la_baicoi);
        addOrderToDatabase("Pizza Hawaiana", 0, 25, R.drawable.hawaiana);
        addOrderToDatabase("Pizza Quatro Scarbosenii", 0, 25, R.drawable.quattro_scarbosenii);
        addOrderToDatabase("Pizza Vegetariana", 0, 25, R.drawable.vegetariana);
    }

    private void initAdapter() {
        Log.d(TAG, "initAdapter: preparing bitmaps.");
        //repopulateDatabase();
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerVIew: init RecyclerViwe");
        recyclerView = findViewById(R.id.recycler_view);
        //adapter = new RecyclerViewAdapter(mNames, mImageUrls, mQuantities, mPrices, MainActivity.this);

        orderEntityList = orderDAO.getAllOrders();
        adapter = new RecyclerViewAdapter(orderEntityList, MainActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




}

