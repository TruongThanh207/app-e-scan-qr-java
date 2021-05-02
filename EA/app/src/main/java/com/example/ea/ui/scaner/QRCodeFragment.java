package com.example.ea.ui.scaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.ea.R;
import com.example.ea.ScannedBarcodeActivity;

public class QRCodeFragment extends Fragment {

    private QRCodeViewModel qrCodeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        qrCodeViewModel = new ViewModelProvider(this).get(QRCodeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_qrcode, container, false);
        final ImageButton btnScan = root.findViewById(R.id.buttonScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScannedBarcodeActivity.class));
            }
        });

        return root;
    }
}