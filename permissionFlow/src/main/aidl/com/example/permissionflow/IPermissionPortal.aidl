package com.example.permissionflow;

import com.example.permissionflow.PermInfo;

interface IPermissionPortal {
    List<PermInfo> getPendingPermissions();
}
