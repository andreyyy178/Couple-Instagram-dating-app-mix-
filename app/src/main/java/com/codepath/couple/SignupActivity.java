package com.codepath.couple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.couple.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";

    EditText etSignUName;
    EditText etSignPassword;
    Button btnSignUpSubmit;
    EditText etGender;
    EditText etAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        etSignUName = findViewById(R.id.etSignUName);
        etSignPassword = findViewById(R.id.etSignPassword);
        btnSignUpSubmit = findViewById(R.id.btnSignUpSubmit);
        etGender = findViewById(R.id.etGender);
        etAge = findViewById(R.id.etAge);


        btnSignUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = etSignPassword.getText().toString();
                String username = etSignUName.getText().toString();
                String gender = etGender.getText().toString();
                Integer age = Integer.valueOf(etAge.getText().toString());

                signup(password, username, gender, age);
            }
        });

    }

    private void signup(String password, String username, String gender, Integer age) {
        // Make new user
        ParseUser user = new ParseUser();

        //Set values for new user
        user.setUsername(username);
        user.setPassword(password);
        user.put("handle", username);
        user.put("Gender", gender);
        user.put("Age", age);


        Intent i = new Intent(this, LoginActivity.class);
        Toast.makeText(SignupActivity.this, "Sign up successs!", Toast.LENGTH_SHORT).show();
        startActivity(i);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i(TAG, "SIGN UP SUCCESS!!!");
                    //DO STUFF
                } else {
                    e.getStackTrace();
                }
            }
        });
    }


}