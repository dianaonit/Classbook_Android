package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.net.Uri;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                DetailsActivity.this.startActivity(intent);
            }
        });


        ImageView imageViewTeacher = findViewById(R.id.imageViewTeacher);
        imageViewTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, CreateAccountActivity.class);
                DetailsActivity.this.startActivity(intent);
            }
        });


        ImageView imageViewStud = findViewById(R.id.imageViewStud);
        imageViewStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, CreateAccountStud.class);
                DetailsActivity.this.startActivity(intent);
            }

        });


        ImageView imageViewParent = findViewById(R.id.imageViewParent);
        imageViewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, CreateAccountParent.class);
                DetailsActivity.this.startActivity(intent);
            }

        });


        ImageButton HelpButton=(ImageButton) findViewById(R.id.HelpButton);
        HelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://econ.elearning.ubbcluj.ro/moodle/"));
                startActivity(browserIntent);
            }
        });

    }
}