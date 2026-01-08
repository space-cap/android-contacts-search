package com.ezlevup.contactssearch

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ezlevup.contactssearch.presentation.ui.ContactListScreen
import com.ezlevup.contactssearch.presentation.ui.permission.PermissionScreen
import com.ezlevup.contactssearch.ui.theme.ContactsSearchTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsSearchTheme {
                // 연락처 권한 상태 관리
                val permissionState =
                        rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)

                // 권한 상태에 따라 화면 전환
                if (permissionState.status.isGranted) {
                    ContactListScreen()
                } else {
                    PermissionScreen(
                            permissionState = permissionState,
                            onPermissionGranted = {
                                // 권한 허용 시 자동으로 ContactListScreen으로 전환됨
                            }
                    )
                }
            }
        }
    }
}
