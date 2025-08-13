package com.example.permissionflow;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

/**
 * Basic unit test that uses ContextCompat with a mocked Activity.
 */
@RunWith(RobolectricTestRunner.class)
public class ContextCompatTest {
    @Test
    public void checkSelfPermissionWithMockActivity() {
        Activity activity = Mockito.mock(Activity.class);
        Mockito.when(activity.checkSelfPermission(Manifest.permission.CAMERA))
                .thenReturn(PackageManager.PERMISSION_DENIED);

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        assertEquals(PackageManager.PERMISSION_DENIED, result);
        Mockito.verify(activity).checkSelfPermission(Manifest.permission.CAMERA);
    }
}
