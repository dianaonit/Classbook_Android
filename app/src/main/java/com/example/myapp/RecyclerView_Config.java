package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    Context mContext;//call activity methods
    GradesAdapter mGradesAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Grade> grades, List<String> keys){
        mContext = context;
        mGradesAdapter = new GradesAdapter(grades, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mGradesAdapter);
    }



    class GradeItemView extends RecyclerView.ViewHolder{
        TextView mSubject;
        TextView mDate;
        TextView mregNr;
        TextView mGrade;

        String key;

        public GradeItemView (ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.activity_grade_item,parent,false));

            mSubject = (TextView) itemView.findViewById(R.id.subject_tv);
            mDate = (TextView) itemView.findViewById(R.id.date_tv);
            mregNr = (TextView) itemView.findViewById(R.id.regNr_tv);
            mGrade = (TextView) itemView.findViewById(R.id.grade_tv);
        }

        public void bind(Grade grade, String key){
            mSubject.setText(grade.getSubject());
            mDate.setText(grade.getDate());
            mregNr.setText(grade.getRegNr().toString());
            mGrade.setText(grade.getGrade().toString());
            this.key=key;
        }

    }

    class GradesAdapter extends RecyclerView.Adapter<GradeItemView>{
       List<Grade> mGradeList;
       List<String> mKeys;

        public GradesAdapter(List<Grade> mGradeList, List<String> mKeys) {
            this.mGradeList = mGradeList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public GradeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GradeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeItemView holder, int position) {
         holder.bind(mGradeList.get(position),mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mGradeList.size();
        }
    }

}
