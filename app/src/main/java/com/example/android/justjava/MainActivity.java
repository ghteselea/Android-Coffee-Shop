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

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

//    Properties
    TextView quantityTextView;
    TextView priceTextView;
    Button addOneBtn;
    Button substractOneBtn;
    Button submitOrderBtn;
    Button sendOrderBtn;

    /*
    * TODO -> Momentan, putem comanda doar un tip de cafea.
    * Ce ne dorim:
    * -> In loc de cafea, vreau sa se comande pizza de pe aplicatia noastra
    * -> nu vom avea un singur tip de pizza, ci mai multe !!!!!!!
    * */

    private int quantity = 0;

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
        quantityTextView = findViewById(R.id.quantity_text_view);
        priceTextView = findViewById(R.id.price_value);
        addOneBtn = findViewById(R.id.plusBtn);
        substractOneBtn = findViewById(R.id.minusBtn);
        submitOrderBtn = findViewById(R.id.submitBtn);
        sendOrderBtn= findViewById(R.id.sendEmailBtn);
//        TODO: - Setup for sendEmailBtn
    }

    private void addListeners() {
        addOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display(++quantity);
                //sendOrderBtn.setEnabled(false);
            }
        });

        substractOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    display(--quantity);
                    //sendOrderBtn.setEnabled(false);
                }
            }
        });

        submitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPrice(quantity * 5);
                Log.d(TAG, "onClick: Quantity is: " + quantity);
                sendOrderBtn.setVisibility(View.VISIBLE);
                //sendOrderBtn.setEnabled(true);
            }
        });

        sendOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendEmail();
                showAlert("Trebuie sa dai pe Order daca ai modificat comanda.");
            }
        });
    }

    private void display(int number) {
        quantityTextView.setText(String.valueOf(number));
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
                "Doresc sa comand " + quantity + " cafele." +
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

