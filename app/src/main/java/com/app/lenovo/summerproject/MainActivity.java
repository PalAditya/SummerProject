package com.app.lenovo.summerproject;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity
{
    int SIGN_IN_REQUEST_CODE=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();
        }
        setContentView(R.layout.activity_weather);
        Bundle bundle=new Bundle();
        bundle.putInt("mode",1);
        WeatherFragment weatherFragment=new WeatherFragment();
        weatherFragment.setArguments(bundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,weatherFragment).commit();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();

            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_city){
            showInputDialog();
        }
        else if(item.getItemId()==R.id.Profile)
        {
            try {
                Intent intent = new Intent(this, StatesData.class);
                startActivity(intent);
            }catch (Exception e)
            {
                Log.e("Couldn't start :( ",e.getMessage());
                /*Super useless change*/
            }
        }
        else if(item.getItemId()==R.id.Toggle)
        {
            String s1=item.getTitle().toString();
            if(s1.equalsIgnoreCase("Current Weather"))
            {
                WeatherFragment wf = (WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.container);
                wf.updateWeatherData(new CityPreference(this).getCity(),1);
                item.setTitle("Predicted Weather");
            }
            else
            {
                WeatherFragment wf = (WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.container);
                wf.updateWeatherData(new CityPreference(this).getCity(),2);
                item.setTitle("Current Weather");
            }
        }
        return false;
    }
    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    public void changeCity(String city){
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.container);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }
}
