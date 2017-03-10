package com.example.bd.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * when the user clicks register we'll get the information from the fields and pass it to register request and then execute the request
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); //this line tells me what xml file it is working with

        // hold reference to field and buttonsin the activity register xml file
        final EditText etAge = (EditText) findViewById(R.id.etAge); // findViewById looks at activity_register file and finds a view named etAge
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        // assign variable to the register button
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        // add listener to register button
        bRegister.setOnClickListener(new View.OnClickListener() {
            // when user clicks register we need to get all the information that was entered
            @Override
            public void onClick(View v) {
                // need to get the name from the name field above
                // .etName.getText gets the text and then need to convert it to a string
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                // gets age and parses age to string
                final int age = Integer.parseInt(etAge.getText().toString());

                // create response listener for below
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // anything in here happens when the response has been executed
                        // volley gives the response, as a String param in the onResponse param list, from the register.php
                        // need to convert to a JSON object so we can work with it because we encoded it into a JSON string in the register.php
                        try {
                            // this can fail if response is not in the form of a json string so need try/catch
                            JSONObject jsonResponse = new JSONObject(response);
                            // from the register.php file when we register the response is a success
                            boolean success = jsonResponse.getBoolean("success"); // we called it "success" in the register.php
                            // when the request is executed and volley gives us a response it will get the value of success
                            // if successful we want to take them back to the login page
                            if (success) {
                                Intent intent =new Intent(RegisterActivity.this, LoginActivity.class);
                                // to start the activity
                                RegisterActivity.this.startActivity(intent);
                            } else /* if not successful we display an error */{
                                // displays error to user
                                // will create error message and say Register failed and when user clicks retry allows register again
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                // set button in dialog box and say retry, create it and then show it.
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // gets string that volley has given and converts it into a json object
                    }
                };
                // create request
                RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, responseListener);
                // have to add it to request queue, get it from volley
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                // add the register request to queue
                queue.add(registerRequest);
            }
        });
    }
}
