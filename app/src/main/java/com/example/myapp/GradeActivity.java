package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GradeActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        Button logout_btn = findViewById(R.id.buttonLogout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                Intent intent=new Intent(GradeActivity.this, MainActivity.class);
                GradeActivity.this.startActivity(intent);
            }
        });

        SharedPreferences userSharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        String userType = userSharedPreferences.getString("User_type", "");
        if (!userType.isEmpty()) {
            ImageButton mNewGrade = findViewById(R.id.imgB_Menu);
            mNewGrade.setVisibility(View.GONE);
        } else {
            ImageButton mNewGrade = findViewById(R.id.imgB_Menu);
            mNewGrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(GradeActivity.this, NewGradeActivity.class);
                    GradeActivity.this.startActivity(intent);
                }
            });
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_grades);

        new FirebaseDatabaseHelper().readGrades(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Grade> grades, List<String> keys) {
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                String regNr = sharedPreferences.getString("User_regNr", "");
                List<Grade> connectedUserGrades = new ArrayList<>();
                if (!regNr.isEmpty()) {
                    for (Grade grade : grades) {
                        if (grade.getRegNr().toString().equals(regNr)) {
                            connectedUserGrades.add(grade);
                        }
                    }
                    new RecyclerView_Config().setConfig(mRecyclerView, GradeActivity.this, connectedUserGrades, keys);
                } else {
                    new RecyclerView_Config().setConfig(mRecyclerView, GradeActivity.this, grades, keys);
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });


    }


}
