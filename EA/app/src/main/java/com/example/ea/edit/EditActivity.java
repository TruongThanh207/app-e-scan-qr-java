package com.example.ea.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ea.MainActivity;
import com.example.ea.R;
import com.example.ea.entity.Device;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.sql.Timestamp;


public class EditActivity extends AppCompatActivity {
    EditText nameProduct, nameManufactory, dateProduct;
    FloatingActionButton btnSave;
    Device device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String result = getIntent().getStringExtra("DATA");

        nameProduct = findViewById(R.id.editTextNameProduct);
        nameManufactory = findViewById(R.id.editManufactory);
        dateProduct = findViewById(R.id.editTextDate);
        btnSave = findViewById(R.id.actionButtonSave);

        setData(result);

        final FirebaseFirestore database = FirebaseFirestore.getInstance();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.collection("devices")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//                                        HashMap<String, String> map = new HashMap<>();
//                                        map.put("timeStamp",String.valueOf(timestamp.getTime()));
//                                        database.collection("devices").document(document.getId()).set(map, SetOptions.merge());
                                        if(device.getManufacture().equals(document.getData().get("manufacture"))
                                                && device.getProductName().equals(document.getData().get("productName"))
                                                && device.getDateOfManufacture().equals(document.getData().get("dateOfManufacture")))
                                        {
                                            //set quantity + 1 when we have same record
                                            device.setQuantity(Integer.parseInt(document.getData().get("quantity").toString()) + 1 );
                                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                            //update field quantity at document id
                                            database.collection("devices").document(document.getId()).update("quantity", device.getQuantity());
                                            database.collection("devices").document(document.getId()).update("timeStamp", String.valueOf(timestamp.getTime()));
                                            Toast.makeText(EditActivity.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(EditActivity.this, MainActivity.class));
                                            finish();
                                            return;
                                        }
//
                                    }
                                }

                                // add record with quantity 1
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                device.setQuantity(1);
                                device.setTimeStamp(String.valueOf(timestamp.getTime()));
                                database.collection("devices").add(device).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(EditActivity.this, "Save Successful!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                startActivity(new Intent(EditActivity.this, MainActivity.class));
                                finish();
                            }
                        });

            }
        });


    }

    private void setData(String result) {
        String [] data = result.split(";", 5);
        device = new Device(data[0], data[1], data[2]);
        //set text
        nameManufactory.setText(data[0]);
        nameProduct.setText(data[1]);
        dateProduct.setText(data[2]);
    }
}