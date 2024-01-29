package com.tabish.easycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv ,solutionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv=findViewById(R.id.result_tv);
        solutionTv=findViewById(R.id.solution_tv);

        assignId(R.id.button_c);
        assignId(R.id.button_close_bracket);
        assignId(R.id.button_open_bracket);
        assignId(R.id.button_divide);
        assignId(R.id.button_multiply);
        assignId(R.id.button_add);
        assignId(R.id.button_subs);
        assignId(R.id.button_equal);
        assignId(R.id.button_0);
        // assignId(R.id.button_1);
        // assignId(R.id.button_2);
        assignId(R.id.button_3);
        assignId(R.id.button_4);
        assignId(R.id.button_5);
        assignId(R.id.button_6);
        assignId(R.id.button_7);
        assignId(R.id.button_8);
        assignId(R.id.button_9);
        assignId(R.id.button_ac);
        assignId(R.id.button_dot);
    }

    void assignId(int id){
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    MaterialButton button =(MaterialButton) view;
    String buttonText=button.getText().toString();
    String dataToCalculate=solutionTv.getText().toString();

    if(buttonText.equals("AC")){
        solutionTv.setText(" ");
        resultTv.setText("0");
        return;
    }
    if (buttonText.equals("=")){
        solutionTv.setText(resultTv.getText());
        return;
    }
    if(buttonText.equals("C")){
        if(dataToCalculate.length()>0)
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
    }
    else{
        dataToCalculate=dataToCalculate+buttonText;
    }

    solutionTv.setText(dataToCalculate);
    String finalResult= getResult(dataToCalculate);

    if(!finalResult.equals("ERR")){
        resultTv.setText(finalResult);
    }
    if(finalResult.equals("org.mozilla.javascript.Undefined@0")){
        resultTv.setText("0");
    }
    }

    String getResult(String data){
        try {
            if(data==null){
                data="0";
            }
            Context context =Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult=finalResult.replace(".0","");
            }
            return finalResult;
        }
        catch (Exception e){
            return "ERR";
        }
    }
}