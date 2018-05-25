package com.app.lenovo.summerproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
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
        c2.setChecked(sharedpreferences.getBoolean("c2",false));
        c3.setChecked(sharedpreferences.getBoolean("c3",false));
        c4.setChecked(sharedpreferences.getBoolean("c4",false));
        c5.setChecked(sharedpreferences.getBoolean("c5",false));
        c6.setChecked(sharedpreferences.getBoolean("c6",false));
        final TextView textView=findViewById(R.id.textView);
        textView.setText(sharedpreferences.getString("date",""));
        CalendarView calendarView=findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                textView.setText(i2+"/"+i1+"/"+i);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=button.getText()+"";
                if(str.equalsIgnoreCase("save"))
                {
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
                    editor.putString("date",textView.getText().toString());
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
                    button.setText("save");
                }
            }
        });

    }
}
