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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.permbus.IPermissionPortal
import androidx.compose.ui.platform.LocalContext
import com.example.permbus.PermInfo

class MainActivity : ComponentActivity() {
    private var portal: IPermissionPortal? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            portal = IPermissionPortal.Stub.asInterface(service)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            portal = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent("com.example.permbus.BIND_PERMISSION_PORTAL")
        intent.`package` = "com.example.appa"
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        setContent {
            PermissionList(portal)
        }
    }
}

@Composable
fun PermissionList(portal: IPermissionPortal?) {
    var list by remember { mutableStateOf<List<PermInfo>>(emptyList()) }

    val context = LocalContext.current
    LaunchedEffect(portal) {
        list = portal?.pendingPermissions ?: emptyList()
    }

    Column {
        if (list.isEmpty()) {
            Text("No permissions to display or portal is null.") // Optional: Handle empty state
        }
        list.forEach { info ->
            Text(text = info.uiLabel)
            Button(onClick = {
                // Now 'context' is captured from the outer composable scope
                try {
                    val intent = Intent.parseUri(info.fixIntentUri, 0)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Handle potential exceptions from parseUri or startActivity
                    // e.g., Log.e("PermissionList", "Error starting activity for fixIntentUri", e)
                    // You might want to show a Toast to the user
                }
            }) {
                Text("Corregir")
            }
        }
    }
}

private val IPermissionPortal.pendingPermissions: List<PermInfo>
    get() = try { getPendingPermissions() } catch (e: Exception) { emptyList() }
