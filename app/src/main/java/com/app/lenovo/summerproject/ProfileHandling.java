package com.app.lenovo.summerproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileHandling extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        sharedpreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        final Button button=findViewById(R.id.button);
        final EditText e1=findViewById(R.id.editText2);
        e1.setText(sharedpreferences.getString("Bp",""));
        final EditText e2=findViewById(R.id.editText4);
        e2.setText(sharedpreferences.getString("Temp",""));
        final EditText e3=findViewById(R.id.editText5);
        e3.setText(sharedpreferences.getString("Other",""));
        final CheckBox c1=findViewById(R.id.checkBox2);
        c1.setChecked(sharedpreferences.getBoolean("c1",false));
        final CheckBox c2=findViewById(R.id.checkBox3);
        final CheckBox c3=findViewById(R.id.checkBox4);
        final CheckBox c4=findViewById(R.id.checkBox5);
        final CheckBox c5=findViewById(R.id.checkBox6);
        final CheckBox c6=findViewById(R.id.checkBox7);
        final CheckBox c7=findViewById(R.id.checkBox8);
        c2.setChecked(sharedpreferences.getBoolean("c2",false));
        c3.setChecked(sharedpreferences.getBoolean("c3",false));
        c4.setChecked(sharedpreferences.getBoolean("c4",false));
        c5.setChecked(sharedpreferences.getBoolean("c5",false));
        c6.setChecked(sharedpreferences.getBoolean("c6",false));
        c7.setChecked(sharedpreferences.getBoolean("c7",false));
        final TextView textView=findViewById(R.id.textView);
        textView.setText(sharedpreferences.getString("Date",""));
        CalendarView calendarView=findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                textView.setText(i2+"/"+i1+"/"+i);
            }
        });
        final Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = {"Nothing particular","Gardening", "Jogging","Evening walk","Reading books",
        "Cooking","Listening to music"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        final Spinner dropdown2 = findViewById(R.id.spinner2);
        String items2[]={"None","Cricket","Football","Tennis","Badminton","Golf"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        try {
            dropdown.setSelection(adapter.getPosition(sharedpreferences.getString("Hobby", "Nothing particular")));
            dropdown2.setSelection(adapter2.getPosition(sharedpreferences.getString("Sport", "None")));
        }catch(Exception e)
        {
            Log.e("Spinner",e.getMessage());
        }
        String s1="",s2="";
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=button.getText()+"";
                if(str.equalsIgnoreCase("save"))
                {
                    HelperClass.putSharedPreferencesString(getApplicationContext(),"Bp",e1.getText().toString());
                    HelperClass.putSharedPreferencesString(getApplicationContext(),"Temp",e2.getText().toString());
                    HelperClass.putSharedPreferencesString(getApplicationContext(),"Other",e3.getText().toString());
                    HelperClass.putSharedPreferencesString(getApplicationContext(),"Date",textView.getText().toString());
                    HelperClass.putSharedPreferencesBoolean(getApplicationContext(),"c1",c1.isChecked());
                    HelperClass.putSharedPreferencesBoolean(getApplicationContext(),"c2",c2.isChecked());
                    HelperClass.putSharedPreferencesBoolean(getApplicationContext(),"c3",c3.isChecked());
                    HelperClass.putSharedPreferencesBoolean(getApplicationContext(),"c4",c4.isChecked());
                    HelperClass.putSharedPreferencesBoolean(getApplicationContext(),"c5",c5.isChecked());
                    HelperClass.putSharedPreferencesBoolean(getApplicationContext(),"c6",c6.isChecked());
                    HelperClass.putSharedPreferencesBoolean(getApplicationContext(),"c7",c7.isChecked());
                    HelperClass.putSharedPreferencesString(getApplicationContext(),"Hobby",dropdown.getSelectedItem().toString());
                    HelperClass.putSharedPreferencesString(getApplicationContext(),"Sport",dropdown2.getSelectedItem().toString());
                    editor=sharedpreferences.edit();
                    editor.putString("Bp",e1.getText().toString());
                    editor.putString("Temp",e2.getText().toString());
                    editor.putString("Other",e3.getText().toString());
                    editor.putBoolean("c1",c1.isChecked());
                    editor.putBoolean("c2",c2.isChecked());
                    editor.putBoolean("c3",c3.isChecked());
                    editor.putBoolean("c4",c4.isChecked());
                    editor.putBoolean("c5",c5.isChecked());
                    editor.putBoolean("c6",c6.isChecked());
                    editor.putBoolean("c7",c7.isChecked());
                    editor.putString("Date",textView.getText().toString());
                    editor.putString("Hobby",dropdown.getSelectedItem().toString());
                    editor.putString("Sport",dropdown2.getSelectedItem().toString());
                    Log.e("Spinners: ",dropdown.getSelectedItem().toString()+","+dropdown2.getSelectedItem().toString());
                    editor.commit();
                    button.setText("edit");
                    e1.setEnabled(false);
                    e2.setEnabled(false);
                    e3.setEnabled(false);
                    c1.setEnabled(false);
                    c2.setEnabled(false);
                    c3.setEnabled(false);
                    c4.setEnabled(false);
                    c5.setEnabled(false);
                    c6.setEnabled(false);
                    c7.setEnabled(false);
                    dropdown.setEnabled(false);
                    dropdown2.setEnabled(false);
                }
                else
                {
                    e1.setEnabled(true);
                    e2.setEnabled(true);
                    e3.setEnabled(true);
                    c1.setEnabled(true);
                    c2.setEnabled(true);
                    c3.setEnabled(true);
                    c4.setEnabled(true);
                    c5.setEnabled(true);
                    c6.setEnabled(true);
                    c7.setEnabled(true);
                    dropdown.setEnabled(true);
                    dropdown2.setEnabled(true);
                    button.setText("save");
                }
            }
        });

    }


}
