package com.example.qianxialedger.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.qianxialedger.data.PreferencesRepository
import kotlinx.coroutines.launch

data class SampleTransaction(val id: Int, val title: String, val amount: String, val avatar: String)

@Composable
fun HomeScreen(repository: PreferencesRepository = PreferencesRepository(LocalContext.current)) {
    val scope = rememberCoroutineScope()
    var selected by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        repository.selectedAvatar.collect { value ->
            selected = value
        }
    }

    val transactions = remember {
        listOf(
            SampleTransaction(1, "早餐 - 咖啡店", "¥12.00", "qianxia_avatar_01.svg"),
            SampleTransaction(2, "公交", "¥3.00", "qianxia_avatar_02.svg"),
            SampleTransaction(3, "购物 - 衣服", "¥199.00", "qianxia_avatar_03.svg")
        )
    }

    val avatars = listOf(
        "qianxia_avatar_01.svg","qianxia_avatar_02.svg","qianxia_avatar_03.svg","qianxia_avatar_04.svg","qianxia_avatar_05.svg",
        "qianxia_avatar_06.svg","qianxia_avatar_07.svg","qianxia_avatar_08.svg","qianxia_avatar_09.svg","qianxia_avatar_10.svg"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("千夏记账", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))

        // big sprite-sheet banner using qianxia002.svg
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(18.dp))
                .border(2.dp, Color(0xFFB6D6FF), RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter("file:///android_asset/avatars/qianxia002.svg"),
                contentDescription = "千夏像素头像集合",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.height(18.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (selected != null) {
                AvatarFromAssets(fileName = selected!!, sizeDp = 96.dp)
            } else {
                Box(modifier = Modifier.size(96.dp).clip(RoundedCornerShape(8.dp)).border(2.dp, Color.Gray, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                    Text("头像")
                }
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text("本月支出 ¥214.00", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(6.dp))
                Row {
                    SmallAvatar("qianxia_avatar_04.svg")
                    Spacer(Modifier.width(6.dp))
                    SmallAvatar("qianxia_avatar_05.svg")
                    Spacer(Modifier.width(6.dp))
                    SmallAvatar("qianxia_avatar_06.svg")
                }
            }

            Spacer(Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.End) {
                Button(onClick = { /* open selector below */ }) { Text("更换头像") }
            }
        }

        Spacer(Modifier.height(18.dp))

        Card(modifier = Modifier.fillMaxWidth().height(96.dp)) {
            Row(modifier = Modifier.fillMaxSize().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("月预算", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("¥1000 - 已用 ¥214", style = MaterialTheme.typography.bodyMedium)
                }
                Row {
                    SmallAvatar("qianxia_avatar_07.svg")
                    Spacer(Modifier.width(6.dp))
                    SmallAvatar("qianxia_avatar_08.svg")
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        Text("最近账单", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(transactions) { tx ->
                TransactionItem(tx)
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("选择头像", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(5), modifier = Modifier.fillMaxWidth().height(180.dp)) {
            items(avatars) { name ->
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(6.dp)) {
                    AvatarItem(name = name, onClick = {
                        scope.launch { repository.setSelectedAvatar(name) }
                    })
                }
            }
        }
    }
}

@Composable
fun TransactionItem(tx: SampleTransaction) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        SmallAvatar(tx.avatar)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(tx.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(4.dp))
            Text(tx.amount, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Text(tx.amount, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AvatarItem(name: String, onClick: () -> Unit) {
    AvatarFromAssets(fileName = name, sizeDp = 64.dp, modifier = Modifier.clickable { onClick() })
}

@Composable
fun SmallAvatar(name: String, sizeDp: androidx.compose.ui.unit.Dp = 32.dp) {
    val requestUri = "file:///android_asset/avatars/$name"
    val painter = rememberAsyncImagePainter(requestUri)
    Image(painter = painter, contentDescription = null, modifier = Modifier.size(sizeDp).clip(RoundedCornerShape(6.dp)).border(1.dp, Color(0xFF00FFD1), RoundedCornerShape(6.dp)))
}

@Composable
fun AvatarFromAssets(fileName: String, sizeDp: androidx.compose.ui.unit.Dp = 96.dp, modifier: Modifier = Modifier) {
    val requestUri = "file:///android_asset/avatars/$fileName"
    val painter = rememberAsyncImagePainter(requestUri)
    Image(painter = painter, contentDescription = null, modifier = modifier.size(sizeDp).clip(RoundedCornerShape(8.dp)).border(2.dp, Color(0xFF00FFD1), RoundedCornerShape(8.dp)))
}
