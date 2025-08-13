package com.example.permissionflow;

import static org.junit.Assert.assertEquals;

import android.Manifest;
import android.content.Intent;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import com.example.permissionflow.ui.PermissionFixActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

/**
 * Tests for {@link PermissionPortalService} using Robolectric.
 */
@RunWith(RobolectricTestRunner.class)
public class PermissionPortalServiceTest {

    private PermissionPortalService service;

    @Before
    public void setUp() {
        service = Robolectric.buildService(PermissionPortalService.class).create().get();
        Application app = ApplicationProvider.getApplicationContext();
        ShadowApplication shadow = Shadows.shadowOf(app);
        shadow.denyPermissions(Manifest.permission.CAMERA);
    }

    @Test
    public void reportsDeniedPermissions() throws Exception {
        Intent bind = new Intent(PermissionPortalService.BIND_PERMISSION_PORTAL);
        IPermissionPortal portal = IPermissionPortal.Stub.asInterface(service.onBind(bind));

        List<PermInfo> list = portal.getPendingPermissions();
        assertEquals(1, list.size());

        PermInfo info = list.get(0);
        assertEquals(Manifest.permission.CAMERA, info.permName);
        Intent fixIntent = Intent.parseUri(info.fixIntentUri, 0);
        assertEquals(PermissionFixActivity.class.getName(), fixIntent.getComponent().getClassName());
        assertEquals(Manifest.permission.CAMERA, fixIntent.getStringExtra("perm"));
    }
}
