package com.example.myapp;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    FirebaseDatabase mDatabase;
    DatabaseReference mReferenceGrades;
    DatabaseReference mReferenceStudent;
    DatabaseReference mReferenceParent;
    List<Grade> grades = new ArrayList<>();
    List<Student> students = new ArrayList<>();
    List<Parent> parents = new ArrayList<>();


    public interface DataStatus{
        void DataIsLoaded(List<Grade> grades, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public interface DataStudentStatus{
        void DataIsLoaded(List<Student> students, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public interface DataParentStatus{
        void DataIsLoaded(List<Parent> parents, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }


    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceGrades = mDatabase.getReference("Grades");
        mReferenceStudent = mDatabase.getReference("Student-Users");
        mReferenceParent = mDatabase.getReference("Parent-Users");

    }

    public void readGrades(final DataStatus dataStatus){
        mReferenceGrades.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                grades.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                   keys.add(keyNode.getKey());
                   Grade grade = keyNode.getValue(Grade.class);
                   grades.add(grade);
                }

                dataStatus.DataIsLoaded(grades, keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readStudents(final DataStudentStatus dataStudentStatus){
        mReferenceStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Student student = keyNode.getValue(Student.class);
                    students.add(student);
                }

                dataStudentStatus.DataIsLoaded(students, keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readParents(final DataParentStatus dataParentStatus){
        mReferenceParent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parents.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Parent parent = keyNode.getValue(Parent.class);
                    parents.add(parent);
                }

                dataParentStatus.DataIsLoaded(parents, keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//
//    public void readTeachers(final DataStatus dataStatus){
//        mReferenceGrades.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                grades.clear();
//                List<String> keys = new ArrayList<>();
//                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
//                    keys.add(keyNode.getKey());
//                    Grade grade = keyNode.getValue(Grade.class);
//                    grades.add(grade);
//                }
//
//                dataStatus.DataIsLoaded(grades, keys);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void addGrade(Grade grade, final DataStatus dataStatus){
      String key =  mReferenceGrades.push().getKey();
      mReferenceGrades.child(key).setValue(grade)
              .addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      dataStatus.DataIsInserted();

                  }

              });


    }

}
