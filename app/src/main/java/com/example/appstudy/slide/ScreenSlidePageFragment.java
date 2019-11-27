package com.example.appstudy.slide;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.appstudy.R;
import com.example.appstudy.question.Question;

import java.util.ArrayList;

public class ScreenSlidePageFragment extends Fragment {
    ArrayList<Question> arr_Ques;
    static final String ARG_PAGE = "pape";
    static final String ARG_CHECKANS = "checkAns";
    int mPageNumber; // vi trí trang hiện tại
    int checkAns; // biến kiểm tra

    TextView tvNum, tvQuestion;
    RadioGroup radioGroup;
    RadioButton rb_a, rb_b, rb_c, rb_d;


    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);


        tvNum = rootView.findViewById(R.id.tvNum);
        tvQuestion = rootView.findViewById(R.id.tvQuestion);
        radioGroup = rootView.findViewById(R.id.radGroup);
        rb_a = rootView.findViewById(R.id.radA);
        rb_b = rootView.findViewById(R.id.radB);
        rb_c = rootView.findViewById(R.id.radC);
        rb_d = rootView.findViewById(R.id.radD);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arr_Ques = new ArrayList<Question>();
        ScreenSlideActivity slideActivity = (ScreenSlideActivity) getActivity();
        arr_Ques = slideActivity.getData();
        mPageNumber = getArguments().getInt(ARG_PAGE);
        checkAns = getArguments().getInt(ARG_CHECKANS);

    }

    public static ScreenSlidePageFragment create(int pageNumber, int checkAns) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_PAGE, pageNumber);
        arg.putInt(ARG_CHECKANS,checkAns);
        fragment.setArguments(arg);
        return fragment;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        tvNum.setText("Câu " + (mPageNumber + 1));
        tvQuestion.setText(getItem(mPageNumber).getQuestion());
        rb_a.setText(getItem(mPageNumber).getAns_a());
        rb_b.setText(getItem(mPageNumber).getAns_b());
        rb_c.setText(getItem(mPageNumber).getAns_c());
        rb_d.setText(getItem(mPageNumber).getAns_d());

        if (checkAns != 0){
            rb_a.setClickable(false);
            rb_b.setClickable(false);
            rb_c.setClickable(false);
            rb_d.setClickable(false);
            getCheckAns(getItem(mPageNumber).getResult().toString());

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                getItem(mPageNumber).choiceID = checkedID;
                getItem(mPageNumber).setTraloi(getAnsID(checkedID));

            }
        });
    }


    private Question getItem(int position) {
        return arr_Ques.get(position);

    }

    // Lấy giá trị chọn radio button trả về list ans
    private String getAnsID(int ID) {
        if (ID == R.id.radA) {
            return "A";
        } else if (ID == R.id.radB) {
            return "B";
        } else if (ID == R.id.radC) {
            return "C";
        } else if (ID == R.id.radD) {
            return "D";
        } else return "";

    }
    // Kiểm tra đáp án đúng
    private void getCheckAns(String ans){
        if (ans.equals("A")){
            rb_a.setBackgroundColor(Color.RED);
        }else if (ans.equals("B")){
            rb_b.setBackgroundColor(Color.RED);
        }else if (ans.equals("C")){
            rb_c.setBackgroundColor(Color.RED);
        }else if (ans.equals("D")){
            rb_d.setBackgroundColor(Color.RED);
        }

    }


}
