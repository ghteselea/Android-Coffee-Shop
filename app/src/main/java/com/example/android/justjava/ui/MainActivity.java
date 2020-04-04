package com.example.android.justjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    Properties
    TextView totalTextView;
    Button submitOrderBtn;
    Button sendOrderBtn;

    private int totalPrice = 0;
    private String finalText = "";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private ArrayList<Integer> mQuantities = new ArrayList<>();
    private ArrayList<Integer> mPrices = new ArrayList<>();

    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    private static final String TAG = "MainActivity";

//    Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
        addListeners();
        initAdapter();
    }

//    Custom methods
    private void setup() {
        totalTextView = findViewById(R.id.price_value);
        submitOrderBtn = findViewById(R.id.submitBtn);
        sendOrderBtn= findViewById(R.id.sendEmailBtn);
    }

    private void addListeners() {
        submitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createFinalString();
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

        createFinalString();

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

    void createFinalString() {
//        totalPrice = pizzaCasQuantity * 25 + pizzaMexQuantity * 28 + pizzaCapQuantity * 30;
//
//        finalText = "Comanda: ";
//        finalText = pizzaCasQuantity == 0 ? finalText : finalText + "\n- " + pizzaCasQuantity + " pizza Casei";
//        finalText = pizzaMexQuantity == 0 ? finalText : finalText + "\n- " + pizzaMexQuantity + " pizza Mexicana ";
//        finalText = pizzaCapQuantity == 0 ? finalText : finalText + "\n- " + pizzaCapQuantity + " pizza Capriciosa";
        //if (pizzaCapQuantity > 0) {
        //   finalText = finalText + "\n- " + pizzaCapQuantity + " pizza Capriciosa";
        //}

        finalText = finalText +
                "\nTotal de plata: " + totalPrice;
    }

    private void initAdapter() {
        Log.d(TAG, "initAdapter: preparing bitmaps.");

        mImageUrls.add(R.drawable.capriciosa);
        mNames.add("Pizza Capriciosa");
        mQuantities.add(0);
        mPrices.add(25);

        mImageUrls.add(R.drawable.morgana);
        mNames.add("Pizza cu mucegai");
        mQuantities.add(0);
        mPrices.add(30);

        mImageUrls.add(R.drawable.vegetariana);
        mNames.add("Pizza da vegani");
        mQuantities.add(0);
        mPrices.add(20);

        mImageUrls.add(R.drawable.capriciosa);
        mNames.add("Pizza cu \'d\' in loc de al doilea \'z\'");
        mQuantities.add(0);
        mPrices.add(24);

        mImageUrls.add(R.drawable.capriciosa);
        mNames.add("Pizza Capriciosa");
        mQuantities.add(0);
        mPrices.add(25);

        mImageUrls.add(R.drawable.morgana);
        mNames.add("Pizza cu mucegai");
        mQuantities.add(0);
        mPrices.add(30);

        mImageUrls.add(R.drawable.vegetariana);
        mNames.add("Pizza da vegani");
        mQuantities.add(0);
        mPrices.add(20);

        mImageUrls.add(R.drawable.capriciosa);
        mNames.add("Pizza cu \'d\' in loc de al doilea \'z\'");
        mQuantities.add(0);
        mPrices.add(24);

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerVIew: init RecyclerViwe");
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(mNames, mImageUrls, mQuantities, mPrices, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




}

