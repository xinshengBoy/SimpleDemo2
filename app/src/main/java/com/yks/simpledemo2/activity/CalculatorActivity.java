package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noober.background.BackgroundLibrary;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;

/**
 * 描述：仿小米计算器（布局）
 * 作者：zzh
 * time:2018/12/14
 * 参考地址：http://www.apkbus.com/blog-873055-79126.html
 */

public class CalculatorActivity extends Activity implements View.OnClickListener{

    private TextView input;
    private Button clear,detele,devide,multi,seven,eight,nine,reduce,four,five,six,add,one,two,three,zero,point,equal;
    private String inputText = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calculator);

        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "计算器");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        input = findViewById(R.id.txt_calculator_input);
        clear = findViewById(R.id.btn_cal_clear);
        detele = findViewById(R.id.btn_cal_detele);
        devide = findViewById(R.id.btn_cal_devide);
        multi = findViewById(R.id.btn_cal_multi);
        seven = findViewById(R.id.btn_cal_seven);
        eight = findViewById(R.id.btn_cal_eight);
        nine = findViewById(R.id.btn_cal_nine);
        reduce = findViewById(R.id.btn_cal_reduce);
        four = findViewById(R.id.btn_cal_four);
        five = findViewById(R.id.btn_cal_five);
        six = findViewById(R.id.btn_cal_six);
        add = findViewById(R.id.btn_cal_add);
        one = findViewById(R.id.btn_cal_one);
        two = findViewById(R.id.btn_cal_two);
        three = findViewById(R.id.btn_cal_three);
        zero = findViewById(R.id.btn_cal_zero);
        point = findViewById(R.id.btn_cal_point);
        equal = findViewById(R.id.btn_cal_equal);

        clear.setOnClickListener(this);
        detele.setOnClickListener(this);
        devide.setOnClickListener(this);
        multi.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        reduce.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        add.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        zero.setOnClickListener(this);
        point.setOnClickListener(this);
        equal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == clear){
            inputText = "";
            input.setText(inputText);
        }else if (view == detele){
            inputText = inputText.substring(0,inputText.length()-1);
            input.setText(inputText);
        }else if (view == devide){
            inputText += "÷";
            input.setText(inputText);
        }else if (view == multi){
            inputText += "×";
            input.setText(inputText);
        }else if (view == seven){
            inputText += "7";
            input.setText(inputText);
        }else if (view == eight){
            inputText += "8";
            input.setText(inputText);
        }else if (view == nine){
            inputText += "9";
            input.setText(inputText);
        }else if (view == reduce){
            inputText += "-";
            input.setText(inputText);
        }else if (view == four){
            inputText += "4";
            input.setText(inputText);
        }else if (view == five){
            inputText += "5";
            input.setText(inputText);
        }else if (view == six){
            inputText += "6";
            input.setText(inputText);
        }else if (view == add){
            inputText += "+";
            input.setText(inputText);
        }else if (view == one){
            inputText += "1";
            input.setText(inputText);
        }else if (view == two){
            inputText += "2";
            input.setText(inputText);
        }else if (view == three){
            inputText += "3";
            input.setText(inputText);
        }else if (view == zero){
            inputText += "0";
            input.setText(inputText);
        }else if (view == point){
            inputText += ".";
            input.setText(inputText);
        }else if (view == equal){
            inputText += "=";
            input.setText(inputText);
        }
    }
}
