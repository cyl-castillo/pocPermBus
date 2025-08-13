package com.example.permissionflow.ui;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;

import com.example.permissionflow.ui.PermissionFixActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;

/**
 * Tests for {@link PermissionFixActivity} using Robolectric.
 */
@RunWith(RobolectricTestRunner.class)
public class PermissionFixActivityTest {

    @Test
    public void requestsPermissionWhenExtraPresent() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), PermissionFixActivity.class);
        intent.putExtra("perm", Manifest.permission.CAMERA);

        PermissionFixActivity activity = Robolectric.buildActivity(PermissionFixActivity.class, intent)
                .create().get();
        ShadowActivity shadow = Shadows.shadowOf(activity);
        ShadowActivity.PermissionsRequest request = shadow.getLastRequestedPermission();

        assertArrayEquals(new String[]{Manifest.permission.CAMERA}, request.requestedPermissions);
        // Permission should still be denied at this point
        int status = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        assertTrue(status == PackageManager.PERMISSION_DENIED);
    }

    @Test
    public void finishesWhenNoPermissionExtra() {
        PermissionFixActivity activity = Robolectric.buildActivity(PermissionFixActivity.class).create().get();
        assertTrue(activity.isFinishing());
    }
}
