package com.example.iot_firebase.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_firebase.R;
import com.example.iot_firebase.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnSignUp;
    private EditText editTextUserName, editTextPassword;
    private List<UserModel> userList;
    public static final String USER_ID_LOGIN = "USER_ID";
    public static final String HOME_ID_LOGIN = "HOME_ID";
    private TextView goToHomeRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUserLoginInfo();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(LoginActivity.this, SignUpActivity_1.class);
                startActivity(signUpActivity);
            }
        });
        goToHomeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeRegisterActivity = new Intent(LoginActivity.this, HomeRegisterActivity.class);
                startActivity(homeRegisterActivity);
            }
        });
    }



    void init()
    {
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnLoginSignUp);
        editTextUserName = findViewById(R.id.editTextLoginUserName);
        editTextPassword = findViewById(R.id.editTextLoginPassword);
        goToHomeRegister = findViewById(R.id.text_home_register);
        userList = new ArrayList<>();
    }

    private void CheckUserLoginInfo() {
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"Enter username or password", Toast.LENGTH_LONG).show();
        }

        Query query = FirebaseDatabase.getInstance().getReference("user")
                .orderByChild("userName").equalTo(userName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists())
                    Toast.makeText(getApplicationContext(), "user invalid", Toast.LENGTH_LONG).show();
                else{
                    for(DataSnapshot data : snapshot.getChildren())
                    {
                        UserModel userModel = data.getValue(UserModel.class);
                        userList.add(userModel);
                    }
                    UserModel user = userList.get(0);
                    if(user.getPassword().equals(password))
                    {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        mainIntent.putExtra(USER_ID_LOGIN, user.getUserId());
                        mainIntent.putExtra(HOME_ID_LOGIN, user.getHomeId());
                        startActivity(mainIntent);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "password not true", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}



