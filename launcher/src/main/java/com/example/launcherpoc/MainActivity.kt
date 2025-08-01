package com.example.launcherpoc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.permbus.IPermissionPortal
import androidx.compose.ui.platform.LocalContext
import com.example.permbus.PermInfo

class MainActivity : ComponentActivity() {
    private val portals = mutableStateListOf<IPermissionPortal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPortal("com.example.appa")
        bindPortal("com.example.appb")

        setContent {
            PermissionList(portals)
        }
    }

    private fun bindPortal(pkg: String) {
        val intent = Intent("com.example.permbus.BIND_PERMISSION_PORTAL")
        intent.`package` = pkg
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                IPermissionPortal.Stub.asInterface(service)?.let { portals += it }
            }
            override fun onServiceDisconnected(name: ComponentName?) {
                // ignored for this sample
            }
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}

@Composable
fun PermissionList(portals: List<IPermissionPortal>) {
    var list by remember { mutableStateOf<List<PermInfo>>(emptyList()) }
    LaunchedEffect(portals) {
        val all = mutableListOf<PermInfo>()
        portals.forEach { portal ->
            all += portal.pendingPermissions
        }
        list = all
    }

    Column {
        val context = LocalContext.current
        list.forEach { info ->
            Text(text = info.uiLabel)
            Button(onClick = {
                val intent = Intent.parseUri(info.fixIntentUri, 0)
                context.startActivity(intent)
            }) {
                Text("Corregir")
            }
        }
    }
}

private val IPermissionPortal.pendingPermissions: List<PermInfo>
    get() = try { getPendingPermissions() } catch (e: Exception) { emptyList() }
