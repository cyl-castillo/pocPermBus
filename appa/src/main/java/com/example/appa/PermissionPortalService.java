package com.example.appa;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.Manifest;

import com.example.permbus.IPermissionPortal;
import com.example.permbus.PermInfo;
import com.example.permbus.ui.PermissionFixActivity;

import java.util.ArrayList;
import java.util.List;

public class PermissionPortalService extends Service {
    private final IPermissionPortal.Stub binder = new IPermissionPortal.Stub() {
        @Override
        public List<PermInfo> getPendingPermissions() {
            List<PermInfo> list = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Intent fix = new Intent(PermissionPortalService.this, PermissionFixActivity.class)
                        .putExtra("perm", Manifest.permission.CAMERA);
                String uri = fix.toUri(0);
                list.add(new PermInfo(Manifest.permission.CAMERA, "Camera", uri));
            }
            return list;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
