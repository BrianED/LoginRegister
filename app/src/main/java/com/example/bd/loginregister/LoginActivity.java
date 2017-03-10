package com.example.bd.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ResponseCache;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);

        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        // when user clicks register link, it will open up register page
        // Listens out for click on register link text view. when user clicks any instruction in onClick method below will be executed
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // want to open register page                           // what page we want to open
                //create Intent that opens register activity
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                // To make activity happen
                // tell the current activity we're on to open/perform the registerIntent intent.
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // more cimplicated than register
                         try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("age", age);

                                LoginActivity.this.startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                // set button in dialog box and say retry, create it and then show it.
                                builder.setMessage("Login failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                // have to add it to request queue, get it from volley
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                // add the register request to queue
                queue.add(loginRequest);
            }
        });
    }
}
