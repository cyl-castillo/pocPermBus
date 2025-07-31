package com.example.permbus;

import com.example.permbus.PermInfo;

interface IPermissionPortal {
    List<PermInfo> getPendingPermissions();
}
