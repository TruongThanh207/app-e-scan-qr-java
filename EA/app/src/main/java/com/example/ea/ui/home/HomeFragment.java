package com.example.ea.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ea.R;
import com.example.ea.edit.UpdateActivity;
import com.example.ea.entity.Device;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.Serializable;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.recycleView);


        Query query = firebaseFirestore.collection("devices").orderBy("timeStamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Device> options = new FirestoreRecyclerOptions.Builder<Device>()
                .setQuery(query, Device.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Device, DeviceViewHolder>(options) {
            @NonNull
            @Override
            public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
                return new DeviceViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final DeviceViewHolder holder, int position, @NonNull final Device model) {
                holder.manufacture.setText(model.getManufacture());
                holder.productName.setText(model.getProductName());
                holder.dateOfManufacture.setText(model.getDateOfManufacture());
                holder.quantity.setText(Integer.toString(model.getQuantity()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Device device = model;
                        //Toast.makeText(getContext(), model.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), UpdateActivity.class);
                        intent.putExtra("data", (Serializable) device);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);




        return root;
    }

    private class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView  manufacture, productName, dateOfManufacture, quantity;

        public DeviceViewHolder(@NonNull final View itemView) {
            super(itemView);
            manufacture = itemView.findViewById(R.id.manufacture);
            productName = itemView.findViewById(R.id.productName);
            dateOfManufacture = itemView.findViewById(R.id.dateOfManufacture);
            quantity = itemView.findViewById(R.id.textQuantity);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


}