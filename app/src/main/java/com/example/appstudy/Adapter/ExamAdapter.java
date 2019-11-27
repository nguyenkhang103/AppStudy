package com.example.appstudy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstudy.R;
import com.example.appstudy.object.Exam;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExamAdapter extends ArrayAdapter {

    public ExamAdapter(@NonNull Context context, ArrayList<Exam> exam) {
        super(context,0, exam);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_exam,parent,false);

        }
        TextView tvName = convertView.findViewById(R.id.tvExam);
        ImageView ivIcon = convertView.findViewById(R.id.ivIcon);

        Exam exams = (Exam) getItem(position);
        if ( exams != null){
            tvName.setText(""+exams.getName());
            ivIcon.setImageResource(R.drawable.ic_test);
        }
        return convertView;
    }
}
