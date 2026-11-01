package ir.danaschool.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF14213D)
private val Blue = Color(0xFF4169E1)
private val Mint = Color(0xFF21B6A8)
private val LightBg = Color(0xFFF4F6FB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DanaSchoolApp() }
    }
}

@Composable
fun DanaSchoolApp() {
    var dark by remember { mutableStateOf(false) }
    var selected by remember { mutableIntStateOf(0) }
    val scheme = if (dark) darkColorScheme(primary = Color(0xFF9DB5FF), secondary = Color(0xFF62D7C9), background = Color(0xFF101827), surface = Color(0xFF182235))
                 else lightColorScheme(primary = Blue, secondary = Mint, background = LightBg, surface = Color.White)
    MaterialTheme(colorScheme = scheme) {
        CompositionLocalProvider(androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl) {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                bottomBar = {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                        val items = listOf("خانه", "برنامه", "تکالیف", "پروفایل")
                        val icons = listOf(Icons.Default.Home, Icons.Default.CalendarMonth, Icons.Default.Assignment, Icons.Default.Person)
                        items.forEachIndexed { index, label ->
                            NavigationBarItem(
                                selected = selected == index,
                                onClick = { selected = index },
                                icon = { Icon(icons[index], contentDescription = label) },
                                label = { Text(label, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            ) { padding ->
                Column(Modifier.fillMaxSize().padding(padding)) {
                    Header(dark, onTheme = { dark = !dark })
                    when (selected) {
                        0 -> HomeScreen(onNavigate = { selected = it })
                        1 -> ScheduleScreen()
                        2 -> TasksScreen()
                        else -> ProfileScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(dark: Boolean, onTheme: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(46.dp).clip(RoundedCornerShape(15.dp)).background(Navy), contentAlignment = Alignment.Center) {
            Text("D", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("DANA SCHOOL", fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface, letterSpacing = 1.sp)
            Text("همراه هوشمند مدرسه دانا", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        IconButton(onClick = onTheme) {
            Icon(if (dark) Icons.Default.LightMode else Icons.Default.DarkMode, contentDescription = "تغییر تم")
        }
        IconButton(onClick = {}) { BadgedBox(badge = { Badge { Text("2") } }) { Icon(Icons.Default.NotificationsNone, contentDescription = "اعلان‌ها") } }
    }
}

@Composable
private fun HomeScreen(onNavigate: (Int) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Column {
                Text("سلام، دانش‌آموز دانا 👋", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("برای یک روز پربار آماده‌ای؟", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
            }
        }
        item {
            Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Navy)) {
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Text("پنل دانش‌آموز", color = Color(0xFFB8C9FF), fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("به دانا خوش آمدی!", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    Text("همه چیز برای یادگیری بهتر، یک‌جا جمع شده.", color = Color.White.copy(alpha = .78f), fontSize = 12.sp)
                    Spacer(Modifier.height(18.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SummaryPill("پایه دهم", Icons.Default.School)
                        SummaryPill("شبکه و نرم‌افزار", Icons.Default.Computer)
                    }
                }
            }
        }
        item {
            Text("دسترسی سریع", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuickTile("برنامه هفتگی", Icons.Default.CalendarMonth, Blue, Modifier.weight(1f)) { onNavigate(1) }
                QuickTile("تکالیف من", Icons.Default.Assignment, Mint, Modifier.weight(1f)) { onNavigate(2) }
                QuickTile("کارنامه", Icons.Default.Grade, Color(0xFF9B72CF), Modifier.weight(1f)) { onNavigate(3) }
            }
        }
        item {
            SectionTitle("اطلاعیه‌های مدرسه", "مشاهده همه")
            NoticeCard("شروع فعالیت سامانه دانا", "نسخه آزمایشی اپلیکیشن آماده بررسی است.", "امروز", Icons.Default.Campaign)
        }
        item {
            SectionTitle("کلاس بعدی", "برنامه کامل")
            Card(shape = RoundedCornerShape(18.dp)) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFE7EDFF)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Computer, contentDescription = null, tint = Blue)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("دانش فنی پایه", fontWeight = FontWeight.Bold)
                        Text("کلاس ۱۰ شبکه • دبیر: ثبت نشده", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text("08:00", color = Blue, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SummaryPill(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(Modifier.clip(CircleShape).background(Color.White.copy(alpha = .13f)).padding(horizontal = 11.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color.White, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(5.dp))
        Text(text, color = Color.White, fontSize = 10.sp)
    }
}

@Composable
private fun QuickTile(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color, modifier: Modifier, onClick: () -> Unit) {
    Card(modifier.clickable(onClick = onClick), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(40.dp).clip(RoundedCornerShape(13.dp)).background(tint.copy(alpha = .12f)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = tint)
            }
            Spacer(Modifier.height(8.dp))
            Text(title, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun SectionTitle(title: String, action: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(title, Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(action, color = Blue, fontSize = 11.sp)
    }
}

@Composable
private fun NoticeCard(title: String, body: String, date: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Blue, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(body, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(date, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ScheduleScreen() {
    val days = listOf("شنبه", "یکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه")
    val subjects = listOf("دانش فنی پایه", "نصب و راه‌اندازی سیستم", "تولید محتوای الکترونیک", "ریاضی", "زبان انگلیسی")
    LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item { Text("برنامه هفتگی", fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("برنامه نمونه؛ اطلاعات رسمی مدرسه هنوز ثبت نشده است.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp) }
        items(days.size) { i ->
            Card(shape = RoundedCornerShape(16.dp)) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(days[i], Modifier.width(72.dp), fontWeight = FontWeight.Bold, color = Blue)
                    Column(Modifier.weight(1f)) { Text(subjects[i], fontWeight = FontWeight.SemiBold); Text("دبیر و ساعت: در انتظار اطلاعات مدرسه", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    Icon(Icons.Default.ChevronLeft, null)
                }
            }
        }
    }
}

@Composable
private fun TasksScreen() {
    LazyColumn(contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("تکالیف و فعالیت‌ها", fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("تکالیف ثبت‌شده توسط دبیران اینجا نمایش داده می‌شود.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp) }
        item { NoticeCard("هنوز تکلیفی ثبت نشده", "با اتصال سامانه مدرسه، تکالیف و مهلت تحویل نمایش داده می‌شوند.", "—", Icons.Default.TaskAlt) }
    }
}

@Composable
private fun ProfileScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("پروفایل دانش‌آموز", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Card(shape = RoundedCornerShape(20.dp)) {
            Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(54.dp).clip(CircleShape).background(Color(0xFFE7EDFF)), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = Blue, modifier = Modifier.size(30.dp)) }
                Spacer(Modifier.width(12.dp))
                Column { Text("دانش‌آموز مهمان", fontWeight = FontWeight.Bold); Text("حساب کاربری هنوز فعال نشده", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
        }
        listOf("اطلاعات فردی", "نمرات و معدل", "آزمون‌های من", "کتابخانه و جزوه‌ها", "ارتباط با مدرسه").forEach {
            Card(shape = RoundedCornerShape(14.dp)) { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Text(it, Modifier.weight(1f)); Icon(Icons.Default.ChevronLeft, null) } }
        }
        Text("برای فعال‌سازی ورود، همگام‌سازی و اطلاعات واقعی، سرور و دسترسی رسمی مدرسه لازم است.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
