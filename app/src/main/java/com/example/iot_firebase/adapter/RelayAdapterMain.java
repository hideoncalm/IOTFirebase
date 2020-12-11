package com.example.iot_firebase.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.iot_firebase.R;
import com.example.iot_firebase.model.RelayModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RelayAdapterMain extends ArrayAdapter<RelayModel> {
    private Context context;
    private int resource;
    private List<RelayModel> relayModelList;

    public RelayAdapterMain(@NonNull Context context, int resource, @NonNull List<RelayModel> relayModelList) {
        super(context, resource, relayModelList);
        this.context = context;
        this.resource = resource;
        this.relayModelList = relayModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        TextView txtRelayName = view.findViewById(R.id.textRelayName);
        ImageView imageViewRelay = view.findViewById(R.id.imageViewSingleRelay);

        RelayModel relay = relayModelList.get(position);
        txtRelayName.setText(relay.getRelayName());
        // turn OFF
        view.findViewById(R.id.btnOffRelay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference relayReference = FirebaseDatabase.getInstance().getReference("relay").child(relay.getRelayId());
                relay.setStatus(0);
                relayReference.setValue(relay);
                imageViewRelay.setBackgroundColor(Color.WHITE);
                Toast.makeText(context, "Turn OFF", Toast.LENGTH_LONG).show();
            }
        });

        // turn ON
        view.findViewById(R.id.btnOnRelay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference relayReference = FirebaseDatabase.getInstance().getReference("relay").child(relay.getRelayId());
                relay.setStatus(1);
                relayReference.setValue(relay);
                imageViewRelay.setBackgroundColor(Color.YELLOW);
                Toast.makeText(context, "Turn ON", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
