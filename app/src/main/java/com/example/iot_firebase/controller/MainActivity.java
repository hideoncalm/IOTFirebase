package com.example.iot_firebase.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_firebase.R;
import com.example.iot_firebase.adapter.RelayAdapterMain;
import com.example.iot_firebase.model.RelayModel;
import com.example.iot_firebase.model.TempHum;
import com.example.iot_firebase.model.UserControlRelay;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private LineChart lineChart;
    private List<RelayModel> relayList;
    private List<UserControlRelay> userControlRelayList;
    private ListView lvRelays;
    private String userId, homeId;
    private TextView textTemp, textHum;

    LineDataSet tempSet, humSet;
    LineData lineData;
    private float i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intentNew = getIntent();
        userId = intentNew.getStringExtra(LoginActivity.USER_ID_LOGIN);
        homeId = intentNew.getStringExtra(LoginActivity.HOME_ID_LOGIN);
        init();
        updateTempAndHum();
        controlListDevicesRelay();
    }

    void init() {
        navigationView = findViewById(R.id.navigationViewId);
        lineChart = findViewById(R.id.lineChart);
        lvRelays = findViewById(R.id.listViewMainActivity);
        textTemp = findViewById(R.id.textViewTemperature);
        textHum = findViewById(R.id.textViewHumidity);
        relayList = new ArrayList<>();
        userControlRelayList = new ArrayList<>();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });
    }

    private void updateTempAndHum() {
        DatabaseReference temReference = FirebaseDatabase.getInstance().getReference("tempHum").child(homeId);
        ArrayList<Entry> yTemp, yHum;
        yTemp = new ArrayList<>();
        yHum = new ArrayList<>();
        temReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TempHum tH = snapshot.getValue(TempHum.class);
                Log.d("tag", tH.toString());
                int temperature, humidity;
                temperature = tH.getTemperature();
                humidity = tH.getHumidity();
                yHum.add(new Entry(i, humidity));
                yTemp.add(new Entry(i, temperature));
                i += 0.25;
                textTemp.setText(String.valueOf(temperature));
                textHum.setText(String.valueOf(humidity));
                tempSet = new LineDataSet(yTemp, "Temperature");
                tempSet.setColor(Color.RED);
                tempSet.setLineWidth(2f);
                tempSet.setDrawValues(false);
                tempSet.setCircleColor(Color.BLACK);
                humSet = new LineDataSet(yHum, "Humidity");
                humSet.setColor(Color.BLUE);
                humSet.setLineWidth(2f);
                humSet.setCircleColor(Color.BLACK);
                humSet.setDrawValues(false);
                lineData = new LineData(tempSet, humSet);
                lineChart.setData(lineData);
                lineChart.setDrawGridBackground(false);
                lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void controlListDevicesRelay() {
        Query query = FirebaseDatabase.getInstance().getReference("userControlRelay")
                .orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userControlRelayList.clear();
                relayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        UserControlRelay userControlRelay = data.getValue(UserControlRelay.class);
                        userControlRelayList.add(userControlRelay);
                    }
                    for (UserControlRelay userCR : userControlRelayList) {
                        String relayId = userCR.getRelayId();
                        Query queryRelay = FirebaseDatabase.getInstance().getReference("relay")
                                .orderByChild("relayId").equalTo(relayId);
                        queryRelay.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot snap : snapshot.getChildren()) {
                                        RelayModel relayModel = snap.getValue(RelayModel.class);
                                        relayList.add(relayModel);
                                    }
                                    RelayAdapterMain adapter = new RelayAdapterMain(MainActivity.this, R.layout.single_relay_layout_main, relayList);
                                    lvRelays.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendUserToLoginActivity() {
        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
        intentLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogin);
        finish();
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                Toast.makeText(this, "log out", Toast.LENGTH_LONG).show();
                sendUserToLoginActivity();
                break;
        }
    }
}