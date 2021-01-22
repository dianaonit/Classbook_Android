package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountParent extends AppCompatActivity implements View.OnClickListener
{
    TextInputLayout firstName,lastName,phone,email,password,registrNrPar;
    Button btnRegister;
    ImageView imgParent;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccountparent);


        ImageView backButtonAccountActivity = findViewById(R.id.backButtonAccountActivity);
        backButtonAccountActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateAccountParent.this, DetailsActivity.class);
                CreateAccountParent.this.startActivity(intent);
            }
        });


        mFirebaseAuth=FirebaseAuth.getInstance();

        imgParent=findViewById(R.id.Parent);
        imgParent.setOnClickListener(this);

        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);
        registrNrPar=findViewById(R.id.register);

        btnRegister=findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.Parent){
            startActivity(new Intent(CreateAccountParent.this,MainActivity.class));
        }else if(id==R.id.buttonRegister){
            registerUser();
        }
    }


    private void registerUser() {
        final String fName=firstName.getEditText().getText().toString().trim();
        final String lName=lastName.getEditText().getText().toString().trim();
        final String Phone=phone.getEditText().getText().toString().trim();
        final String Email=email.getEditText().getText().toString().trim();
        final String Pass=password.getEditText().getText().toString().trim();
        final String regNrPar=registrNrPar.getEditText().getText().toString().trim();

        boolean error=false;

        if(fName.isEmpty()){
            firstName.setError(getResources().getString(R.string.empty_errfName));
            firstName.requestFocus();
            error=true;
        }else{
            firstName.setError(null);
            firstName.requestFocus();
        }

        if(lName.isEmpty()){
            lastName.setError(getResources().getString(R.string.empty_errlName));
            lastName.requestFocus();
            error=true;
        }else{
            lastName.setError(null);
            lastName.requestFocus();
        }

        if(Phone.isEmpty()){
            phone.setError(getResources().getString(R.string.empty_errPhone));
            phone.requestFocus();
            error=true;
        }else{
            phone.setError(null);
            phone.requestFocus();
        }

        if(Email.isEmpty()){
            email.setError(getResources().getString(R.string.empty_errEmail));
            email.requestFocus();
            error=true;
        }else{
            email.setError(null);
            email.requestFocus();
        }

        if(Pass.isEmpty()){
            password.setError(getResources().getString(R.string.empty_errPass));
            password.requestFocus();
            error=true;
        }else if(Pass.length() < 6){
            password.setError(getResources().getString(R.string.length_errPass));
            password.requestFocus();
            error=true;
        }else{
            password.setError(null);
            password.requestFocus();
        }


        if(regNrPar.isEmpty()){
            registrNrPar.setError(getResources().getString(R.string.empty_regNr));
            registrNrPar.requestFocus();
            error=true;
        }else if(regNrPar.length() != 5){
            registrNrPar.setError(getResources().getString(R.string.length_regNr));
            registrNrPar.requestFocus();
            error=true;
        }else{
            registrNrPar.setError(null);
            registrNrPar.requestFocus();
        }


        if(!error) {
            mFirebaseAuth.createUserWithEmailAndPassword(Email, Pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Parent parent = new Parent(fName, lName, Phone, Email, Pass, regNrPar);
                                FirebaseDatabase.getInstance().getReference("Parent-Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(parent).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CreateAccountParent.this, getResources().getString(R.string.reg_successful), Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(CreateAccountParent.this, MainActivity.class));

                                        } else {
                                            Toast.makeText(CreateAccountParent.this, getResources().getString(R.string.reg_failed), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(CreateAccountParent.this, getResources().getString(R.string.reg_failed), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }


}
