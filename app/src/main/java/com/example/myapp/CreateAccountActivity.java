package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener
{
    TextInputLayout firstName,lastName,phone,email,password,subject;
    Button btnRegister;
    ImageView imgTeacher;
    FirebaseAuth mFirebaseAuth;
    CheckBox cbA,cbB,cbC,cbD;
    String Classes= "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);


        ImageView backButtonAccountActivity = findViewById(R.id.backButtonAccountActivity);
        backButtonAccountActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateAccountActivity.this, DetailsActivity.class);
                CreateAccountActivity.this.startActivity(intent);
            }
        });

        mFirebaseAuth=FirebaseAuth.getInstance();

        imgTeacher=findViewById(R.id.Teacher1);
        imgTeacher.setOnClickListener(this);

        firstName=findViewById(R.id.firstName1);
        lastName=findViewById(R.id.lastName);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);
        subject=findViewById(R.id.Subject3);

        cbA=findViewById(R.id.checkBoxA);
        cbB=findViewById(R.id.checkBoxB);
        cbC=findViewById(R.id.checkBoxC);
        cbD=findViewById(R.id.checkBoxD);

        btnRegister=findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.Teacher1){
            startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));
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
     final String Subject=subject.getEditText().getText().toString().trim();

     boolean error=false;



     if(fName.isEmpty()){
         firstName.setError(getResources().getString(R.string.empty_errfName));
         firstName.requestFocus();
         error=true;
     }else {
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
     }else {
         email.setError(null);
         email.requestFocus();
     }

//     if(Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
//         email.setError("Introduceti o adresa valida!");
//         email.requestFocus();
//         return;
//     }

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

     if(Subject.isEmpty()){
         subject.setError(getResources().getString(R.string.empty_errSubject));
         subject.requestFocus();
         error=true;
     }else{
         subject.setError(null);
         subject.requestFocus();
     }


     if(!cbA.isChecked() && !cbB.isChecked() && !cbC.isChecked() && !cbD.isChecked()){

            Toast.makeText(CreateAccountActivity.this,getResources().getString(R.string.toast_chooseClass),Toast.LENGTH_LONG).show();
     }

     else {

         if(cbA.isChecked()){
             Classes=Classes+" "+cbA.getText();
         }

         if(cbB.isChecked()){
             Classes=Classes+" "+cbB.getText();
         }

         if(cbC.isChecked()){
             Classes=Classes+" "+cbC.getText();
         }

         if(cbD.isChecked()){
             Classes=Classes+" "+cbD.getText();
         }

          if(!error) {
              mFirebaseAuth.createUserWithEmailAndPassword(Email, Pass)
                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if (task.isSuccessful()) {
                                  Teacher teacher = new Teacher(fName, lName, Phone, Email, Pass, Subject, Classes);
                                  FirebaseDatabase.getInstance().getReference("Teacher-Users")
                                          .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                          .setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {
                                          if (task.isSuccessful()) {
                                              Toast.makeText(CreateAccountActivity.this, getResources().getString(R.string.reg_successful), Toast.LENGTH_LONG).show();
                                              startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                                          } else {
                                              Toast.makeText(CreateAccountActivity.this, getResources().getString(R.string.reg_failed), Toast.LENGTH_LONG).show();
                                          }
                                      }
                                  });
                              } else {
                                  Toast.makeText(CreateAccountActivity.this, getResources().getString(R.string.reg_failed), Toast.LENGTH_LONG).show();
                              }
                          }
                      });
          }
     }


    }


}
