package com.example.ea.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ea.MainActivity;
import com.example.ea.R;
import com.example.ea.entity.Device;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;

public class UpdateActivity extends AppCompatActivity {

    private EditText manufacture, dateOfManufacture, productName, quantity;
    private Button update, remove;
    private Device device;
    private String documentId;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = FirebaseFirestore.getInstance();

        getData();

        getId();

        mapping(device);



        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateActivity.this);
                alertDialog.setTitle("Remove");
                alertDialog.setMessage("Are you sure to delete them all? ");
                alertDialog.setIcon(R.drawable.ic_warning);


                alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        database.collection("devices").document(documentId).delete();
                        Toast.makeText(UpdateActivity.this, "Remove successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                        finish();
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(quantity.getText().toString())) {
                    Toast.makeText(UpdateActivity.this, "Number box cannot be empty !", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(quantity.getText().toString()) == device.getQuantity()){
                    Toast.makeText(UpdateActivity.this,  "Nothing to update", Toast.LENGTH_LONG).show();
                } else{
                    //
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    database.collection("devices").document(documentId).update("quantity", Integer.parseInt(quantity.getText().toString()));
                    database.collection("devices").document(documentId).update("timeStamp", String.valueOf(timestamp.getTime()));

                    Toast.makeText(UpdateActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                    finish();
                    //
                }
            }
        });

    }

    private void getId() {
        database.collection("devices").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
//
                        if(device.getManufacture().equals(document.getData().get("manufacture"))
                                && device.getProductName().equals(document.getData().get("productName")))
                        {
                            documentId = document.getId();
                        }
//
                    }
                }
            }
        });
    }

    private void mapping(Device device) {
        remove = findViewById(R.id.buttonRemove);
        update = findViewById(R.id.buttonUpdate);

        manufacture = findViewById(R.id.editManufactory);
        dateOfManufacture = findViewById(R.id.editTextDate);
        productName = findViewById(R.id.editTextNameProduct);
        quantity = findViewById(R.id.editTextQuantity);

        //set data
        manufacture.setText(device.getManufacture());
        dateOfManufacture.setText(device.getDateOfManufacture());
        productName.setText(device.getProductName());
        quantity.setText(Integer.toString(device.getQuantity()));
    }

    private void getData() {
        device = (Device) getIntent().getSerializableExtra("data");
    }
}
