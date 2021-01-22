package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NewGradeActivity extends AppCompatActivity {


    EditText mSubject_editText;
    EditText mDate_editText;
    EditText mRegNr_editText;
    EditText mGrade_editText;
    Button mAdd_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgrade);

        Button back_btn = findViewById(R.id.buttonBack);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewGradeActivity.this, GradeActivity.class);
                NewGradeActivity.this.startActivity(intent);
            }
        });

        mSubject_editText = (EditText) findViewById(R.id.editTextSubject);
        mDate_editText = (EditText) findViewById(R.id.editTextDate);
        mRegNr_editText = (EditText) findViewById(R.id.editTextregNr);
        mGrade_editText = (EditText) findViewById(R.id.editTextGrade);

        mAdd_btn = (Button) findViewById(R.id.buttonAddGrade);
        mAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = createGrade();
                if (!error) {
                    Grade grade = new Grade();
                    grade.setSubject(mSubject_editText.getText().toString());
                    grade.setDate(mDate_editText.getText().toString());
                    grade.setRegNr(Long.parseLong(mRegNr_editText.getText().toString()));
                    grade.setGrade(Long.parseLong(mGrade_editText.getText().toString()));

                    new FirebaseDatabaseHelper().addGrade(grade, new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<Grade> grades, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {
                            Toast.makeText(NewGradeActivity.this, getResources().getString(R.string.insertgrade_successful), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });
                }else {
                    Toast.makeText(NewGradeActivity.this, getResources().getString(R.string.insertgrade_faild), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private boolean createGrade() {

        final String subject = mSubject_editText.getText().toString().trim();
        final String date = mDate_editText.getText().toString().trim();
        final String regNr = mRegNr_editText.getText().toString().trim();
        final String grade = mGrade_editText.getText().toString().trim();


        boolean error = false;

        //validare materie
        if (subject.isEmpty()) {
            mSubject_editText.setError(getResources().getString(R.string.empty_errmSubj));
            mSubject_editText.requestFocus();
            error = true;
        } else {
            mSubject_editText.setError(null);
            mSubject_editText.requestFocus();
        }

        //validare data
        if (date.isEmpty()) {
            mDate_editText.setError(getResources().getString(R.string.empty_errmData));
            mDate_editText.requestFocus();
            error = true;
        } else {
            mDate_editText.setError(null);
            mDate_editText.requestFocus();
        }

        //validare nr inregistrare
        if (regNr.isEmpty()) {
            mRegNr_editText.setError(getResources().getString(R.string.empty_errmRegNr));
            mRegNr_editText.requestFocus();
            error = true;
        } else if (regNr.length() != 5) {
            mRegNr_editText.setError(getResources().getString(R.string.length_errmRegNr));
            mRegNr_editText.requestFocus();
            error = true;
        } else {
            mRegNr_editText.setError(null);
            mRegNr_editText.requestFocus();
        }


        //validare nota
        if (grade.isEmpty()) {
            mGrade_editText.setError(getResources().getString(R.string.empty_errmGrade));
            mGrade_editText.requestFocus();
            error = true;
        } else {
            int grade1 = Integer.parseInt(grade);
            if (grade1 <= 0 || grade1 > 10) {
                mGrade_editText.setError(getResources().getString(R.string.length_errmGrade));
                mGrade_editText.requestFocus();
                error = true;
            } else {
                mGrade_editText.setError(null);
                mGrade_editText.requestFocus();
            }
        }
         return  error;


    }


}
