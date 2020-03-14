package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

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
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

//    Properties
    TextView pizzaCasTv;
    Button plusPizzaCasBtn;
    Button minusPizzaCasBtn;
    TextView pizzaMexTv;
    Button plusPizzaMexBtn;
    Button minusPizzaMexBtn;
    TextView pizzaCapTv;
    Button plusPizzaCapBtn;
    Button minusPizzaCapBtn;
    TextView priceTextView;
    Button submitOrderBtn;
    Button sendOrderBtn;

    /*
    * TODO -> Momentan, putem comanda doar un tip de cafea.
    * Ce ne dorim:
    * -> In loc de cafea, vreau sa se comande pizza de pe aplicatia noastra
    * -> nu vom avea un singur tip de pizza, ci mai multe !!!!!!!
    * */

    private int pizzaCasQuantity = 0;
    private int pizzaMexQuantity = 0;
    private int pizzaCapQuantity = 0;
    private int totalPrice = 0;

    private static final String TAG = "MainActivity";

//    Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
        addListeners();
    }

//    Custom methods
    private void setup() {

        pizzaCasTv = findViewById(R.id.pizzaCasTv);
        plusPizzaCasBtn = findViewById(R.id.plusPizzaCasBtn);
        minusPizzaCasBtn = findViewById(R.id.minusPizzaCasBtn);



        priceTextView = findViewById(R.id.price_value);
        submitOrderBtn = findViewById(R.id.submitBtn);
        sendOrderBtn= findViewById(R.id.sendEmailBtn);
//        TODO: - Setup for sendEmailBtn
    }

    private void addListeners() {
        plusPizzaCasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizzaCasQuantity++;
                pizzaCasTv.setText(String.valueOf(pizzaCasQuantity));
                sendOrderBtn.setEnabled(false);
            }
        });

        minusPizzaCasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pizzaCasQuantity > 0) {
                    pizzaCasQuantity--;
                    pizzaCasTv.setText(String.valueOf(pizzaCasQuantity));
                    sendOrderBtn.setEnabled(false);
                }
            }
        });

        submitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPrice = pizzaCasQuantity * 25 + pizzaCapQuantity * 30 + pizzaMexQuantity * 28;
                displayPrice(totalPrice);
                if(totalPrice > 0 ) {
                    sendOrderBtn.setVisibility(View.VISIBLE);
                    sendOrderBtn.setEnabled(true);
                }
            }
        });

        sendOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert("Ësti sigur ca vrei sa finalizezi comanda?");
            }
        });
    }


    private void displayPrice(int number){
        priceTextView.setText(NumberFormat.getCurrencyInstance(new Locale("ro_RO", "RO")).format(number));
    }

    private void sendEmail() {

        String email = "gteselea@yahoo.com";

        Intent emailIntent = new Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + email));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Comanda mea");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Doresc sa comand " + totalPrice + " cafele." +
                        "\nTotal de plata: " + priceTextView.getText());
        startActivity(Intent.createChooser(emailIntent, "Alege ce vrei sa folosesti"));
    }

    private void showAlert(String title) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage(title);
        alert.setCancelable(true);

        alert.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        sendEmail();
                    }
                });

        alert.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Ätunci esti bulangiu", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

}

