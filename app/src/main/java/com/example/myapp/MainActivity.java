package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {
    ConstraintLayout constraintLayout;

    String languageCode;

    TextInputLayout inputEmail, inputPassword;
    Button signIn;
    FirebaseAuth mFirebaseAuth;


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

        mFirebaseAuth=FirebaseAuth.getInstance();

        signIn=findViewById(R.id.buttonSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                if(id==R.id.buttonSignIn){
                    userLogin();
                }
            }
        });

        inputEmail=findViewById(R.id.textInputLayout2);
        inputPassword=findViewById(R.id.textInputLayout);


    }



    private void showChangeLanguageDialog() {
        Resources resources = getResources();
        final String[] listItems = {resources.getString(R.string.languageTypeRo),resources.getString(R.string.languageTypeEn), resources.getString(R.string.languageTypeDe),resources.getString(R.string.languageTypeFr)};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(R.string.Language);
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

    private void userLogin() {

        final String email=inputEmail.getEditText().getText().toString().trim();
        String password=inputPassword.getEditText().getText().toString().trim();
        boolean error=false;

        if(email.isEmpty()){
            inputEmail.setError(getResources().getString(R.string.empty_errEmail));
            inputEmail.requestFocus();
            error=true;
           // return;
        }else {
            inputEmail.setError(null);
            inputEmail.requestFocus();
        }

        if(password.isEmpty()){
            inputPassword.setError(getResources().getString(R.string.empty_errPass));
            inputPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            inputPassword.setError(getResources().getString(R.string.length_errPass));
            inputPassword.requestFocus();
            error=true;

        }else{
            inputPassword.setError(null);
            inputPassword.requestFocus();
        }

        if(!error) {
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        new Thread(new Runnable() {
                            public void run() {
                                //redirectionare spre pagina userului
                                saveStudentRegNr(email);
                                saveParentRegNr(email);
                                startActivity(new Intent(MainActivity.this,GradeActivity.class));
                            }
                        }).start();
                    } else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }

    private void saveStudentRegNr(final String email) {
        new FirebaseDatabaseHelper().readStudents(new FirebaseDatabaseHelper.DataStudentStatus() {
            @Override
            public void DataIsLoaded(List<Student> students, List<String> keys) {
                for(Student student : students) {
                    if(student.Email.equalsIgnoreCase(email)) {
                        SharedPreferences.Editor editor = getSharedPreferences("User", MODE_PRIVATE).edit();
                        editor.putString("User_regNr", student.regNr);
                        editor.putString("User_type", "student");
                        editor.commit();
                    }
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

    private void saveParentRegNr(final String email) {
        new FirebaseDatabaseHelper().readParents(new FirebaseDatabaseHelper.DataParentStatus() {
            @Override
            public void DataIsLoaded(List<Parent> parents, List<String> keys) {
                for(Parent parent : parents) {
                    if(parent.Email.equalsIgnoreCase(email)) {
                        SharedPreferences.Editor editor = getSharedPreferences("User", MODE_PRIVATE).edit();
                        editor.putString("User_regNr", parent.regNrPar);
                        editor.putString("User_type", "parent");
                        editor.commit();
                    }
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
