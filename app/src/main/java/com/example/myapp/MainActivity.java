package com.example.myapp;

import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    String languageCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPreferences = getSharedPreferences("Language", MODE_PRIVATE);
        languageCode = sharedPreferences.getString("Selected_language", "");
        if (!languageCode.isEmpty()) {
            setLocale(languageCode);
        }

        setContentView(R.layout.activity_main);

        ImageButton LanguageButton = (ImageButton) findViewById(R.id.LanguageButton);
        LanguageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                showChangeLanguageDialog();
            }

        });

        TextView textViewCreateUser = findViewById(R.id.textViewCreateUser);
        textViewCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        constraintLayout = findViewById(R.id.container);

    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"Romanian", "English", "German", "French"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language...");
        int checkedItem;
        if(languageCode.equalsIgnoreCase("ro")){
            checkedItem = 0;
        } else if( languageCode.equalsIgnoreCase("en")) {
            checkedItem = 1;
        } else if(languageCode.equalsIgnoreCase("de")){
            checkedItem = 2;
        } else if(languageCode.equalsIgnoreCase("fr")){
            checkedItem = 3;
        } else{
            checkedItem = -1;
        }
        mBuilder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("ro");
                } else if (i == 1) {
                    setLocale("en");
                } else if (i == 2) {
                    setLocale("de");
                } else if (i == 3) {
                    setLocale("fr");
                }
                dialogInterface.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });


        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String localeCode) {
        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Language", MODE_PRIVATE).edit();
        editor.putString("Selected_language", localeCode);
        editor.apply();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }
}
