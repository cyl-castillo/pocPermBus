package com.example.permissionflow.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

public class PermissionFixActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String perm = getIntent().getStringExtra("perm");
        if (perm != null) {
            ActivityCompat.requestPermissions(this, new String[]{perm}, 1);
        } else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        finish();
    }
}
