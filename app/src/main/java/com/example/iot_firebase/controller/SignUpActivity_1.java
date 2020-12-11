package com.example.iot_firebase.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_firebase.R;

public class SignUpActivity_1 extends AppCompatActivity {
    private EditText editTextUserName, editTextPassword,
            editTextUserFullName, editTextConfirmPassword;

    private Button btn;
    public static final String USER_FULL_NAME = "USER_FULL_NAME";
    public static final String USER_NAME = "USER__NAME";
    public static final String PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_1);

        editTextUserName = findViewById(R.id.editTextUserNameSignUp1);
        editTextUserFullName = findViewById(R.id.editTextUserFullNameSignUp1);
        editTextPassword = findViewById(R.id.editTextPasswordSignUp1);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordSignUp1);
        btn = findViewById(R.id.btnContinueRegister);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, password, confirmPassword, userFullName;

                userName = editTextUserName.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                userFullName = editTextUserFullName.getText().toString().trim();
                confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(userFullName))
                    Toast.makeText(SignUpActivity_1.this,
                            "user name or password or confirm password empty", Toast.LENGTH_LONG).show();
                else if (!password.equals(confirmPassword))
                    Toast.makeText(SignUpActivity_1.this, "password is not equals", Toast.LENGTH_LONG).show();
                else {
                    Intent signUpActivity_2 = new Intent(getApplicationContext(), SignUpActivity_2.class);
                    signUpActivity_2.putExtra(USER_FULL_NAME, userFullName);
                    signUpActivity_2.putExtra(USER_NAME, userName);
                    signUpActivity_2.putExtra(PASSWORD, password);
                    startActivity(signUpActivity_2);
                }
            }
        });
    }
}
