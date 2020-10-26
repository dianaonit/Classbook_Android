package com.example.myapp;
import androidx.appcompat.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
     ConstraintLayout constraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
        }


        constraintLayout=findViewById(R.id.container);

        TextView textViewCreateUser = findViewById(R.id.textViewCreateUser);
        textViewCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, DetailsActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ImageButton LanguageButton = (ImageButton)findViewById(R.id.LanguageButton);
        LanguageButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                showChangeLanguageDialog();
            }

        });

    }

    private void showChangeLanguageDialog(){
          final String[] listItems={"Romanian","English","German","French"};
          AlertDialog.Builder mBuilder= new AlertDialog.Builder(MainActivity.this);
          mBuilder.setTitle("Choose Language...");
          mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  if(i==0){
                      setLocale("fr");
                      recreate();
                  }

                 else if(i==1){
                      setLocale("en");
                      recreate();
                  }

                  else if(i==2){
                      setLocale("de-rDE");
                      recreate();
                  }
                  else if(i==3){
                      setLocale("ro-rRO");
                      recreate();
                  }

                  dialogInterface.dismiss();

              }
          });


          AlertDialog mDialog=mBuilder.create();
          mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;

        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
     public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("My_lang","");
        setLocale(language);
     }

}
