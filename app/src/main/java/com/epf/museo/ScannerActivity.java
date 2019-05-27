package com.epf.museo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.common.StringUtils;

import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerActivity  extends Activity implements ZXingScannerView {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
        mScannerView.setFormats(Collections.singletonList(BarcodeFormat.QR_CODE));
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here



        String result = rawResult.getText();
        Log.v("scan", String.valueOf(result.length()));

        if(validID(result)) {
            Toast.makeText(getApplicationContext(),"Valid Code:"+result, Toast.LENGTH_SHORT).show();
            finish();
            Intent MuseeActivity = new Intent(ScannerActivity.this, MuseeActivity.class);
            getIntent().putExtra("result",result);
            startActivity(MuseeActivity);


        } else {

            Toast.makeText(getApplicationContext(),"Invalid QR Code:"+result, Toast.LENGTH_SHORT).show();
            mScannerView.resumeCameraPreview(this);
        }

    }
    private boolean validID(String id) {
        return (id.matches("[a-z0-9]*") && id.length() == 40);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}



