package com.example.varsha.chatserver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    Button login;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText et = (EditText)findViewById( R.id.username );


        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.name = et.getText().toString();

                 if (et.getText().toString().trim().isEmpty() || et.getText().toString().contains(" "))
                 {
                   Toast.makeText( getApplicationContext(), "Username is required! Spaces are not allowed.", Toast.LENGTH_LONG ).show();
                } else {
                  //Toast.makeText( getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG ).show();
                  //   Toast.makeText( getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG ).show();
                user.setName( et.getText().toString() );
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory( GsonConverterFactory.create() )
                        .baseUrl( "https://chat.promactinfo.com/api/" )
                        .build();

                Service service = retrofit.create( Service.class );
                service.login( user ).enqueue( new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            //Log.d( "Success", response.body().toString() );
                            Toast.makeText( getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG ).show();
                            Intent intent = new Intent(getApplicationContext(),UserList1.class);
                            startActivity(intent);
                            Toast.makeText( getApplicationContext(),response.body().token, Toast.LENGTH_LONG ).show();
                            //Log.d( "Success", response.body().token );
                            SharedPreferences preferences = getSharedPreferences( "myPrefs", MODE_PRIVATE );
                            preferences.edit().putString( "token", response.body().token ).apply();
                            token = preferences.getString( "token", "" );
                            //Log.d( "Success token ", token );
                        } else {
                            Toast.makeText( getApplicationContext(), "Failure", Toast.LENGTH_LONG ).show();
                            Log.d( "Failure", response.errorBody().toString() );
                        }
                    }


                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                } );
            }


            }});
    }
}
