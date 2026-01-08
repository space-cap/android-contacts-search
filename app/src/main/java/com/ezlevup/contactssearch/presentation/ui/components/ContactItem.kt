package com.ezlevup.contactssearch.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ezlevup.contactssearch.data.model.Contact

/**
 * 개별 연락처 아이템 UI 컴포넌트
 *
 * @param contact 연락처 정보
 * @param onClick 클릭 이벤트 콜백
 * @param modifier Modifier
 */
@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Row(
            modifier =
                    modifier.fillMaxWidth()
                            .clickable(onClick = onClick)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        // 프로필 아이콘 (실제 앱에서는 photoUri를 사용하여 이미지 표시 가능)
        Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "프로필",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 이름과 전화번호
        Column(modifier = Modifier.weight(1f)) {
            Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
            )
            Text(
                    text = contact.phoneNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
