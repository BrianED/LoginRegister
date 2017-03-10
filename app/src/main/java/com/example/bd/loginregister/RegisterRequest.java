package com.example.bd.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * make a request to the register.php file on server and get response as a string, hence extends StringRequest
 * SUMMARY: When the request is executed volley will call getParams() and it will return params which we have filled with the data
 * as seen in the params.put section
 * Created by bd on 31/01/2017.
 */
public class RegisterRequest extends StringRequest {

    // Need to specify the url where our register.php file is
    private static final String REGISTER_REQUEST_URL = "http://sentimental-leather.000webhostapp.com/register.php/";

    private Map<String, String> params;

    // create constructor, this is the first method run when an instance of this class is created
    public RegisterRequest(String name, String username, int age, String password, Response.Listener<String> listener) {
        // need to pass data to volley which will allow it to execute our request
        // Method.POST means we're going to send some data to register.php and register.php will respond with some data
        // next give volley URL of request which we created REGISTER_REQUEST_URL
        // Listener below - when volley is finished with the request its going to inform the Response.Listener in the constructor
        super(Method.POST, REGISTER_REQUEST_URL, listener, null); // error listener is null for now, volley will inform if error with request
        // need way for volley to pass name, age, username, password from register.php to the request
        // need to use params^ firt by creating it
        params = new HashMap<>();
        // now actually need to put data into the hashmap, do that by params.put(String key, String value) with our 4 params
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        // have to add string to convert to string
        params.put("age", age + "");
    }
    // now volley needs to access this data
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
