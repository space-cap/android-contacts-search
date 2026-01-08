package com.ezlevup.contactssearch.presentation.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

/**
 * 연락처 권한 요청 화면
 *
 * @param permissionState 권한 상태
 * @param onPermissionGranted 권한 허용 시 콜백
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(permissionState: PermissionState, onPermissionGranted: () -> Unit) {
    val context = LocalContext.current

    // 권한이 이미 허용된 경우
    if (permissionState.status.isGranted) {
        onPermissionGranted()
        return
    }

    Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Icon(
                imageVector = Icons.Default.Contacts,
                contentDescription = "연락처",
                modifier = Modifier.padding(16.dp),
                tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
                text = "연락처 접근 권한 필요",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
                text =
                        if (permissionState.status.shouldShowRationale) {
                            "이 앱은 연락처 정보를 읽어와 검색 기능을 제공합니다.\n" + "연락처 권한을 허용해주세요."
                        } else {
                            "이 앱은 연락처 정보를 읽어와 검색 기능을 제공합니다.\n" + "아래 버튼을 눌러 권한을 허용해주세요."
                        },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
                onClick = {
                    if (permissionState.status.shouldShowRationale) {
                        // 권한이 거부된 경우 설정으로 이동
                        val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                        context.startActivity(intent)
                    } else {
                        // 권한 요청
                        permissionState.launchPermissionRequest()
                    }
                }
        ) {
            Text(
                    text =
                            if (permissionState.status.shouldShowRationale) {
                                "설정으로 이동"
                            } else {
                                "권한 허용"
                            }
            )
        }
    }
}
