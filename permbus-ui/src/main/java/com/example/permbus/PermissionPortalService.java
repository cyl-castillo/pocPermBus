package com.example.permbus;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.IBinder;

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
            try {
                PackageInfo pkgInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
                String[] perms = pkgInfo.requestedPermissions;
                if (perms != null) {
                    for (String perm : perms) {
                        if (checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                            Intent fix = new Intent(PermissionPortalService.this, PermissionFixActivity.class)
                                    .putExtra("perm", perm);
                            list.add(new PermInfo(perm, perm, fix.toUri(0)));
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                // should not happen
            }
            return list;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
