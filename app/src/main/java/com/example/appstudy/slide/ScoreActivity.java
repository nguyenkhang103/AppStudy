package com.example.appstudy.slide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.appstudy.R;
import com.example.appstudy.question.Question;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {
    ArrayList<Question> arr_Ques = new ArrayList<>();
    int no_Ans=0;
    int ans_true=0;
    int ans_false=0;
    int totalScore = 0;

    TextView tvAnsTrue, tvAnsFalse, tvNoAns, tvTotalScore;
    Button btnSave, btnRestart, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.
        setContentView(R.layout.activity_score);

        final Intent intent = getIntent();
        arr_Ques = (ArrayList<Question>) intent.getExtras().getSerializable("arr_ans");
        state();
        checkResult();
        totalScore = ans_true * 10;
        tvNoAns.setText(""+no_Ans);
        tvAnsTrue.setText(""+ans_true);
        tvAnsFalse.setText(""+ans_false);
        tvTotalScore.setText(""+totalScore);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(ScoreActivity.this);
                builder.setIcon(R.drawable.ic_question);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có thực sự muốn thoát hay không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    public void state(){
        tvAnsFalse = findViewById(R.id.tvFalse);
        tvAnsTrue = findViewById(R.id.tvTrue);
        tvNoAns = findViewById(R.id.tvNotAns);
        btnSave = findViewById(R.id.btnSaveScore);
        btnExit = findViewById(R.id.btnExit);
        btnRestart = findViewById(R.id.btnAgain);
        tvTotalScore = findViewById(R.id.tvTotalPoint);
    }

    // Kiem tra dap an
    public void checkResult(){
        for (int i = 0; i < arr_Ques.size();i++){
            if (arr_Ques.get(i).getTraloi().equals("") == true){
                no_Ans++;
            }
            else if (arr_Ques.get(i).getResult().equals(arr_Ques.get(i).getTraloi())== true){
                ans_true++;
            }
            else ans_false++;
        }

    }
}
