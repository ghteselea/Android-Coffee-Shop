package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

//    Properties
    TextView quantityTextView;
    TextView priceTextView;
    Button addOneBtn;
    Button substractOneBtn;
    Button submitOrderBtn;
//    TODO: - Add sendEmailBtn

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
//        TODO: - Setup for sendEmailBtn
    }

    private void addListeners() {
        addOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display(++quantity);
            }
        });

        substractOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    display(--quantity);
                }
            }
        });

        submitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPrice(quantity * 5);
                Log.d(TAG, "onClick: Quantity is: " + quantity);

//                TODO: - call sendEmail() in the listener of the sendEmailBtn
                sendEmail();
            }
        });

//        TODO: - Add listener to the sendEmailBtn
    }

    private void display(int number) {
        quantityTextView.setText(String.valueOf(number));
    }

    private void displayPrice(int number){
        priceTextView.setText(NumberFormat.getCurrencyInstance(new Locale("ro_RO", "RO")).format(number));
    }

    private void sendEmail() {

        String email = "enachescurobert@gmail.com";

        Intent emailIntent = new Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + email));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Comanda mea");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Doresc sa comand " + quantity + " cafele." +
                        "\nTotal de plata: " + priceTextView.getText());
        startActivity(Intent.createChooser(emailIntent, "Alege ce vrei sa folosesti"));
    }

}

