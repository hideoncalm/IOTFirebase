package com.example.iot_firebase.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_firebase.R;
import com.example.iot_firebase.adapter.RelayAdapter;
import com.example.iot_firebase.model.HomeModel;
import com.example.iot_firebase.model.RelayModel;
import com.example.iot_firebase.model.UserControlRelay;
import com.example.iot_firebase.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity_2 extends AppCompatActivity {

    private TextView textViewUserFullName;
    private Spinner homeNameSpinner;
    private Button btnRegister;
    private ListView lvDevices, lvDevicesChoose;
    private List<HomeModel> listHomeModel;
    private List<String> listHomeName;
    private List<RelayModel> listRelayModel, listRelayChoose;
    private DatabaseReference homeReference;
    private String userFulName, userName, password;
    private String homeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_2);
        init();
        // get data user tu signUpActivity_1
        Intent intent = getIntent();
        userFulName = intent.getStringExtra(SignUpActivity_1.USER_FULL_NAME);
        userName = intent.getStringExtra(SignUpActivity_1.USER_NAME);
        password = intent.getStringExtra(SignUpActivity_1.PASSWORD);

        textViewUserFullName.setText(userFulName);

        getAllHomeFromDatabase();
        registerUser();

    }

    void init() {
        textViewUserFullName = findViewById(R.id.textViewUserFullNameSignUp);
        homeNameSpinner = findViewById(R.id.spinnerHomeName);
        btnRegister = findViewById(R.id.btnRegister);
        lvDevices = findViewById(R.id.listViewDevices);
        lvDevicesChoose = findViewById(R.id.listViewDevicesChoose);
        listHomeModel = new ArrayList<>();
        listRelayModel = new ArrayList<>();
        listRelayChoose = new ArrayList<>();
        listHomeName = new ArrayList<>();
    }

    private void getAllHomeFromDatabase() {
        homeReference = FirebaseDatabase.getInstance().getReference().child("home");
        homeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listHomeModel.clear();
                listHomeName.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        HomeModel homeModel = data.getValue(HomeModel.class);
                        listHomeModel.add(homeModel);
                        listHomeName.add(homeModel.getHomeName());
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(SignUpActivity_2.this, R.layout.support_simple_spinner_dropdown_item, listHomeName);
                homeNameSpinner.setAdapter(arrayAdapter);
                homeNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HomeModel homeModel = listHomeModel.get(position);
                        Toast.makeText(SignUpActivity_2.this, "Selected " + homeModel.getHomeName(), Toast.LENGTH_LONG).show();
                        homeId = homeModel.getHomeId();
                        getAllDevicesInHome();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllDevicesInHome() {
        Query query = FirebaseDatabase.getInstance().getReference("relay").orderByChild("homeId").equalTo(homeId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listRelayModel.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        RelayModel homeModel = data.getValue(RelayModel.class);
                        listRelayModel.add(homeModel);
                    }
                }
                RelayAdapter relayAdapter = new RelayAdapter(SignUpActivity_2.this, listRelayModel);
                lvDevices.setAdapter(relayAdapter);
                lvDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(SignUpActivity_2.this, "selected Relay", Toast.LENGTH_LONG).show();
                        RelayModel relay = listRelayModel.get(position);
                        listRelayChoose.add(relay);
                        RelayAdapter relayAdapterChoose = new RelayAdapter(SignUpActivity_2.this, listRelayChoose);
                        lvDevicesChoose.setAdapter(relayAdapterChoose);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void registerUser() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeReference = FirebaseDatabase.getInstance().getReference("user");
                // them user vao bang user
                String userId = homeReference.push().getKey();
                UserModel userModel = new UserModel(userName, password, userFulName, userId, homeId);

                homeReference.child(userId).setValue(userModel);

                // them cac relay ma user chon vao bang userControlRelay
                homeReference = FirebaseDatabase.getInstance().getReference("userControlRelay");
                for (RelayModel relayModel : listRelayChoose) {
                    String UCL_Id = homeReference.push().getKey();
                    UserControlRelay userControlRelay = new UserControlRelay(userId, relayModel.getRelayId());
                    homeReference.child(UCL_Id).setValue(userControlRelay);
                }
                // send user to main activity

                Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(getApplicationContext(), LoginActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });
    }


}

