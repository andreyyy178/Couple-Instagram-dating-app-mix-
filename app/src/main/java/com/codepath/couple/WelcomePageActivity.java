package com.codepath.couple;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class WelcomePageActivity extends AppCompatActivity {

    public static final String TAG = "WelcomePageActivity";

    private Button btnLogin;
    private Button btnSignup;
    private TextView tvApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        tvApp = findViewById(R.id.tvApp);
        Typeface typeface = ResourcesCompat.getFont(
                this,
                R.font.rajdhanilight);
        tvApp.setTypeface(typeface);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginAcitivty();

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignupActivity();
            }
        });
    }

    private void goSignupActivity() {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    private void goLoginAcitivty() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}
