package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountStud extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccountstud);


        ImageView backButtonAccountActivity = findViewById(R.id.backButtonAccountActivity);
        backButtonAccountActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateAccountStud.this, DetailsActivity.class);
                CreateAccountStud.this.startActivity(intent);
            }
        });
    }
}
