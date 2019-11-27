package com.example.appstudy.monhoc;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.appstudy.Adapter.ExamAdapter;
import com.example.appstudy.MainActivity;
import com.example.appstudy.R;
import com.example.appstudy.object.Exam;
import com.example.appstudy.slide.ScreenSlideActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AVFragment extends Fragment {


    GridView gridView;
    ExamAdapter examAdapter;
    ArrayList<Exam> exams = new ArrayList<Exam>();

    public AVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Môn Tiếng Anh");
        return inflater.inflate(R.layout.fragment_av, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView = getActivity().findViewById(R.id.gvSubject);
        exams.add(new Exam("Đề số 1"));
        exams.add(new Exam("Đề số 2"));
        exams.add(new Exam("Đề số 3"));
        exams.add(new Exam("Đề số 4"));

        examAdapter = new ExamAdapter(getActivity(),exams);
        gridView.setAdapter(examAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ScreenSlideActivity.class);
                intent.putExtra("subject","english");
                startActivity(intent);
            }
        });

    }
}
