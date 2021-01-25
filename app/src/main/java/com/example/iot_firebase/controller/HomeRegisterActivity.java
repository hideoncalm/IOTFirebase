package com.example.iot_firebase.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_firebase.R;
import com.example.iot_firebase.adapter.RelayAdapter;
import com.example.iot_firebase.model.HomeModel;
import com.example.iot_firebase.model.RelayModel;
import com.example.iot_firebase.model.TempHum;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeRegisterActivity extends AppCompatActivity {
    private EditText homeName, deviceName;
    private ListView lvDeviceAdd;
    private Button addDevice, homeRegister;
    private List<RelayModel> relayList;
    private DatabaseReference reference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_register);
        init();
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String device = deviceName.getText().toString();
                RelayModel relayModel = new RelayModel();
                relayModel.setRelayName(device);
                relayList.add(relayModel);
                RelayAdapter relayAdapter = new RelayAdapter(HomeRegisterActivity.this, relayList);
                lvDeviceAdd.setAdapter(relayAdapter);
            }
        });
        homeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homeId = addHomeToFireBase();
                addTempHumToFireBase(homeId);
                addDevicesToFireBase(homeId);
                Toast.makeText(HomeRegisterActivity.this, "Register Home Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private String addHomeToFireBase() {
        reference = FirebaseDatabase.getInstance().getReference("home");
        String homeId = reference.push().getKey();
        HomeModel newHome = new HomeModel(homeId, homeName.getText().toString());
        reference.child(homeId).setValue(newHome);
        return homeId;
    }
    private void addTempHumToFireBase(String homeId) {
        reference = FirebaseDatabase.getInstance().getReference("tempHum");
        String tempHumId = reference.push().getKey();
        TempHum newTempHum = new TempHum();
        newTempHum.setHomeId(homeId);
        newTempHum.setTempHumId(tempHumId);
        reference.child(homeId).setValue(newTempHum);
    }
    private void addDevicesToFireBase(String homeId) {
        reference = FirebaseDatabase.getInstance().getReference("relay");
        for (RelayModel relayModel : relayList){
            String relayId = reference.push().getKey();
            relayModel.setRelayId(relayId);
            relayModel.setRelayName(deviceName.getText().toString());
            relayModel.setHomeId(homeId);
            relayModel.setStatus(0);
            reference.child(relayId).setValue(relayModel);
        }
    }


    private void init(){
        homeName = findViewById(R.id.home_name_edit);
        deviceName = findViewById(R.id.devices_name_edit);
        lvDeviceAdd = findViewById(R.id.listViewDevicesAdd);
        addDevice = findViewById(R.id.btnAddDevice);
        homeRegister = findViewById(R.id.btnRegisterHome);
        relayList = new ArrayList<>();
    }
}
