package com.example.ea.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.ea.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    TextView company, productType, productTotal;
    FirebaseFirestore database;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        company = root.findViewById(R.id.textCompany);
        productTotal = root.findViewById(R.id.textTotal);
        productType = root.findViewById(R.id.textType);

        database = FirebaseFirestore.getInstance();

        getCompany();
        //company.setText(getCompany());
        getProductType();
        getProductTotal();

        return root;
    }

    private void getCompany() {
        final String[] count = new String[1];
        final ArrayList<String> data = new ArrayList<>();
        database.collection("devices")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snap : task.getResult()){
                                int flag = 0;
                                for(int i=0; i< data.size(); i++)
                                {
                                    if(snap.getData().get("manufacture").toString().equals(data.get(i))) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if(flag == 0){
                                    data.add(snap.getData().get("manufacture").toString());
                                };
                            }
                            company.setText(String.valueOf(data.size()));
                            data.clear();
                        }
                    }
                });
    }

    private void getProductType() {

        final ArrayList<Object> data = new ArrayList<>();
        database.collection("devices")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snap : task.getResult()) {
                                int flag = 0;
                                for (int i = 0; i < data.size(); i++) {
                                    if (snap.getData().get("productName").equals(data.get(i))) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if (flag == 0) {
                                    data.add(snap.getData().get("productName"));
                                }
                                ;
                            }
                            productType.setText(String.valueOf(data.size()));
                            data.clear();
                        }
                    }
                });


    }


    private void getProductTotal() {

        database.collection("devices")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int sum = 0;
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snap : task.getResult()){
                                sum += Integer.parseInt(snap.getData().get("quantity").toString()) ;
                            }
                        }
                        productTotal.setText(String.valueOf(sum));
                    }
                });

    }
}