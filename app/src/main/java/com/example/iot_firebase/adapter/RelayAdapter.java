package com.example.iot_firebase.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.iot_firebase.R;
import com.example.iot_firebase.model.RelayModel;

import java.util.List;

public class RelayAdapter extends ArrayAdapter<RelayModel> {

    private Activity context;
    private List<RelayModel> relayList;
    public RelayAdapter(Activity context, List<RelayModel> relayList){
        super(context, R.layout.single_relay_layout, relayList);
        this.context = context;
        this.relayList = relayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.single_relay_layout, null, true);

        TextView relayName = view.findViewById(R.id.textViewSingleDevice);
        RelayModel relayModel = relayList.get(position);
        relayName.setText(relayModel.getRelayName());

        return view;
    }
}
