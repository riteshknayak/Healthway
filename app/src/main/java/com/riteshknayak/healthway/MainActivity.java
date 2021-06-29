package com.riteshknayak.healthway;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Boolean textColor = false;
    private Boolean cmUnit = false;
    private Boolean poundUnit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reset_button = findViewById(R.id.reset_button);
        reset_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText foot = findViewById(R.id.foot_value);
                foot.setText(null);
                EditText inch = findViewById(R.id.inch_value);
                inch.setText(null);
                EditText weight = findViewById(R.id.weight_value);
                weight.setText(null);
                TextView bmiText = findViewById(R.id.text_your_bmi);
                bmiText.setText(null);
                TextView bmi_value = findViewById(R.id.bmi_value);
                bmi_value.setText(null);
                TextView cmValue = findViewById(R.id.cm_value);
                cmValue.setText(null);


            }
        });


        EditText foot = findViewById(R.id.foot_value);
        EditText inch = findViewById(R.id.inch_value);
        EditText weight = findViewById(R.id.weight_value);
        EditText cmValue = findViewById(R.id.cm_value);

        foot.addTextChangedListener(textWatcher);
        inch.addTextChangedListener(textWatcher);
        weight.addTextChangedListener(textWatcher);
        cmValue.addTextChangedListener(textWatcher);

        Spinner spinner = findViewById(R.id.height_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.height_unit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.weight_unit);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.weight_unit, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (text.equals("Ft+in")){
            EditText foot = findViewById(R.id.foot_value);
            EditText inch = findViewById(R.id.inch_value);
            EditText cm = findViewById(R.id.cm_value);
            foot.setVisibility(View.VISIBLE);
            inch.setVisibility(View.VISIBLE);
            cm.setVisibility(View.GONE);
            cmUnit = false;
        }
        else if (text.equals("CM")){
            EditText cm = findViewById(R.id.cm_value);
            EditText foot = findViewById(R.id.foot_value);
            EditText inch = findViewById(R.id.inch_value);
            cm.setVisibility(View.VISIBLE);
            foot.setVisibility(View.GONE);
            inch.setVisibility(View.GONE);
            cmUnit = true;
        }
        else if (text.equals("Pound")){
            poundUnit = true;
            calculate();
        }

        else if (text.equals("KG")){
            poundUnit = false;
            calculate();
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    public void calculate() {
        TextView bmi_value = findViewById(R.id.bmi_value);
        TextView bmiText = findViewById(R.id.text_your_bmi);
        String textForBMI = "Your BMI is ";
        bmiText.setText(textForBMI);
        bmi_value.setText(getBMI());
        if (textColor) {
            bmi_value.setTextColor(Color.parseColor("#18CA1F"));
            bmiText.setTextColor(Color.parseColor("#18CA1F"));
        } else {
            bmi_value.setTextColor(Color.parseColor("#FA0000"));
            bmiText.setTextColor(Color.parseColor("#FA0000"));
        }

        TextView weightMin = findViewById(R.id.healthy_weight_min);
        TextView weightMax = findViewById(R.id.healthy_weight_max);
        TextView and = findViewById(R.id.healthy_weight_and);
        TextView healthyWeight = findViewById(R.id.healthy_weight);

        weightMin.setText(getMinHealthyWeight());
        weightMax.setText(getMaxHealthyWeight());
        weightMin.setVisibility(View.VISIBLE);
        weightMax.setVisibility(View.VISIBLE);
        and.setVisibility(View.VISIBLE);
        healthyWeight.setVisibility(View.VISIBLE);
    }

    ;

    public double getFoot() {
        EditText foot = findViewById(R.id.foot_value);
        String str = foot.getText().toString();
        double D = 0;
        if (cmUnit){}
        else{
            if (str.equals("")) {}
            else {
                D = Integer.parseInt(foot.getText().toString()) / 3.281;
            }
        }
        return D;
    }

    ;

    public double getInch() {
        EditText inch = findViewById(R.id.inch_value);
        String str = inch.getText().toString();
        double v = 0;
        if (cmUnit) {}

        else {
            if (str.equals("")) {
            } else {
                v = Integer.parseInt(inch.getText().toString()) / 39.37;
            }
        }

        return v;

    }

    ;

    public Double getHeight() {
        DecimalFormat df = new DecimalFormat("#.##");
        double v = 0.0;
        if(cmUnit){
            EditText cmValue = findViewById(R.id.cm_value);
            if (cmValue.getText().toString().equals("")){}

            else {
                v = Double.parseDouble(cmValue.getText().toString())/100;
            }
        }
        else{
            v = getFoot() + getInch();
        }
        return Double.parseDouble(df.format(v));
    }


    public double getWeight() {
        double z = 0;
        EditText weight = findViewById(R.id.weight_value);
        String str = weight.getText().toString();
        if (str.equals("")) {}
        else {
            if (poundUnit){
                z = Double.parseDouble(weight.getText().toString())/2.205;
            }
            else{
                z = Double.parseDouble(weight.getText().toString());
            }
        }
        return z;
    }

    public String getBMI() {
        double bmi = 0;
        if (getWeight() == 0 || getHeight() == 0) {
        } else {
            double v = getWeight() / (getHeight() * getHeight());
            DecimalFormat df = new DecimalFormat("#.#");
            bmi = Double.parseDouble(df.format(v));
            if (bmi < 24.5 && bmi > 18.5) {
                textColor = true;
            } else {
                textColor = false;
            }
        }
        return Double.toString(bmi);
    }

    public String  getMinHealthyWeight(){
        double v = getHeight()*getHeight()*18.5;
        DecimalFormat df = new DecimalFormat("###.#");
        return  df.format(v)+"KG";

    }

    public String getMaxHealthyWeight(){
        double v = getHeight()*getHeight()*24.5;
        DecimalFormat df = new DecimalFormat("###.#");
        return  df.format(v)+"KG";
    }



    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            calculate();
        }

    };

}
