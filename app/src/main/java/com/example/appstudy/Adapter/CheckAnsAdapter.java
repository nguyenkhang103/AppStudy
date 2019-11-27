package com.example.appstudy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appstudy.R;
import com.example.appstudy.question.Question;

import java.util.ArrayList;

public class CheckAnsAdapter extends BaseAdapter {
    ArrayList lsData;
    LayoutInflater layoutInflater;

    public CheckAnsAdapter(ArrayList lsData, Context context) {
        this.lsData = lsData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsData.size();
    }

    @Override
    public Object getItem(int i) {
        return lsData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int postion, View view, ViewGroup viewGroup) {
        Question question = (Question) getItem(postion);
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_gridview_ans,null);
            viewHolder.tvNumQues = view.findViewById(R.id.tvNumQues);
            viewHolder.tvYourAns = view.findViewById(R.id.tvAns);
           view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        int i = postion +1;
        viewHolder.tvNumQues.setText("Câu "+i+":");
        viewHolder.tvYourAns.setText(question.getTraloi());
        return view;
    }

    public static class ViewHolder{
        TextView tvNumQues, tvYourAns;
    }
}
