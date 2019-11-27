package com.example.appstudy.slide;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.appstudy.Adapter.CheckAnsAdapter;
import com.example.appstudy.MainActivity;
import com.example.appstudy.R;
import com.example.appstudy.question.DBHelper;
import com.example.appstudy.question.Question;
import com.example.appstudy.question.QuestionController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class ScreenSlideActivity extends FragmentActivity {

    private static final int NUM_PAGES = 10;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    ArrayList<Question> arr_ques;


    CounterClass timer;
    TextView tvKiemTra, tvTimer, tvScore;
    String subject1;
    int checkAns = 0;

//    private DBHelper dbHelper;
//    final String DATABASE_NAME ="db_tracnghiem.sqlite";
//    SQLiteDatabase sqLiteDatabase;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.paper);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        Intent intent = getIntent();
        subject1 = intent.getStringExtra("subject");
        timer = new CounterClass(5*60*1000,1000);


//        arr_ques = new ArrayList<Question>();
//        arr_ques = QuestionController.getInstance(this).getQuestion();

        ArrayList<Question> questionTypeList = QuestionController.getInstance(this).getQuestion();



        tvKiemTra = findViewById(R.id.tvKiemTra);
        tvTimer = findViewById(R.id.tvTimer);
        tvScore = findViewById(R.id.tvScore);
        tvKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAns();

            }
        });
        tvScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ScreenSlideActivity.this,ScoreActivity.class);
                intent.putExtra("arr_ans",arr_ques);
                startActivity(intent);
            }
        });
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        timer.start();
    }


//     @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private ArrayList<Question> getQuestion() {
//        ArrayList <Question> lsData = new ArrayList<>();
//
//            sqLiteDatabase = DBHelper.initDatabase(this,DATABASE_NAME);
//            Cursor cursor  = sqLiteDatabase.rawQuery("SELECT * FROM tracnghiem",null);
//            cursor.moveToFirst();
//
//            do {
//                Question item;
//                item = new Question(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8), cursor.getString(9), "");
//                lsData.add(item);
//            } while (cursor.moveToNext());
//
//            return lsData;
//        }


    public ArrayList<Question> getData(){
        return arr_ques;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position,checkAns);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
    public void checkAns(){
        final Dialog checkQuestion = new Dialog(ScreenSlideActivity.this,R.style.FilterDialogTheme);
        checkQuestion.setContentView(R.layout.check_ans_dialog);
        checkQuestion.setTitle("Danh sách đáp án");

        CheckAnsAdapter ansAdapter = new CheckAnsAdapter(arr_ques,this);
        GridView gvLsQuestion = checkQuestion.findViewById(R.id.glQuestion);
        gvLsQuestion.setAdapter(ansAdapter);

        gvLsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mPager.setCurrentItem(i);
                checkQuestion.dismiss();
            }
        });

        Button btnCancel, btnSub;
        btnCancel = checkQuestion.findViewById(R.id.btnCancel);
        btnSub = checkQuestion.findViewById(R.id.btnSub);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkQuestion.dismiss();
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                result();
                checkQuestion.dismiss();

            }
        });
        checkQuestion.show();

    }
    public void result(){
        if (mPager.getCurrentItem() <= 4) mPager.setCurrentItem(mPager.getCurrentItem()+4);
        else if (mPager.getCurrentItem() > 4)mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        checkAns = 1;
        tvScore.setVisibility(View.VISIBLE);
        tvKiemTra.setVisibility(View.GONE);
    }
    public class CounterClass extends CountDownTimer {


        //millisInFuture: 60*1000
        //countDownInterval:  1000
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
             @SuppressLint("DefaultLocale") String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");  //SetText cho textview hiện thị thời gian.
//            result();
        }
    }

}
