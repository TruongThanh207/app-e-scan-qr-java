package com.example.ea.ui.scanned;


import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.ea.MainActivity;
import com.example.ea.R;
import com.example.ea.ScannedBarcodeActivity;
import com.google.zxing.Result;

public class QRCodeFragment extends Fragment {
    private QRCodeViewModel qrCodeViewModel;
//    private CodeScanner mCodeScanner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        qrCodeViewModel = new ViewModelProvider(this).get(QRCodeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_qrcode, container, false);
        final Button btnScan = root.findViewById(R.id.buttonScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScannedBarcodeActivity.class));
            }
        });

        return root;
    }
}
