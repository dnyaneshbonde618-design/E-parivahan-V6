package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SystemNotification
import com.example.model.UserRole
import com.example.ui.theme.BorderCyanGlow
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.MagentaNeon
import com.example.ui.theme.SurfaceCardDark
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MainTab

data class NavItemSpec(
    val tab: MainTab,
    val label: String,
    val subtitle: String,
    val icon: ImageVector,
    val testTag: String
)

val NAV_ITEMS = listOf(
    NavItemSpec(MainTab.HOME, "Home", "Dashboard", Icons.Default.Dashboard, "nav_home"),
    NavItemSpec(MainTab.ENERGY, "Energy", "Energy Section", Icons.Default.Bolt, "nav_energy"),
    NavItemSpec(MainTab.DATA, "Data", "Stored Database", Icons.Default.Storage, "nav_data"),
    NavItemSpec(MainTab.CREDIT, "Credit", "System Credits", Icons.Default.Info, "nav_credit")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandCenterTopBar(
    activeTab: MainTab,
    loggedInUser: String,
    loggedInRole: UserRole,
    notifications: List<SystemNotification>,
    onOpenDrawer: () -> Unit,
    onSimulateVehicle: () -> Unit,
    onSimulateSuspicious: () -> Unit,
    onResetBaseline: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showNotifDialog by remember { mutableStateOf(false) }
    var showSimMenu by remember { mutableStateOf(false) }

    val pageTitle = when (activeTab) {
        MainTab.HOME -> "DASHBOARD"
        MainTab.ENERGY -> "ENERGY MANAGEMENT"
        MainTab.DATA -> "STORED DATABASE"
        MainTab.CREDIT -> "CREDITS"
    }

    Surface(
        color = SurfaceDark,
        tonalElevation = 3.dp,
        modifier = modifier.border(BorderStroke(1.dp, BorderDark))
    ) {
        TopAppBar(
            title = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "E PARIVAHAN V6",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.2.sp,
                                fontSize = 15.sp
                            ),
                            color = com.example.ui.theme.GreenPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(BorderDark)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "STUDENT DEMO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = com.example.ui.theme.GreenAccent
                            )
                        }
                    }
                    Text(
                        text = pageTitle,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp
                        ),
                        color = TextPrimary
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier.testTag("drawer_menu_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Navigation Menu",
                        tint = TextPrimary
                    )
                }
            },
            actions = {
                // Interactive simulation trigger for reviewers
                IconButton(
                    onClick = { showSimMenu = true },
                    modifier = Modifier.testTag("sim_menu_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Simulate Telemetry",
                        tint = CyanNeon
                    )
                }

                // Notification Bell
                IconButton(
                    onClick = { showNotifDialog = true },
                    modifier = Modifier.testTag("notifications_button")
                ) {
                    BadgedBox(
                        badge = {
                            Badge(containerColor = CyanNeon, contentColor = Color.Black) {
                                Text(notifications.size.toString())
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = TextSecondary
                        )
                    }
                }

                // Small user / profile indicator
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(SurfaceCardDark)
                        .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(CyanNeon)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = loggedInUser.ifEmpty { "User" },
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            ),
                            color = TextPrimary
                        )
                    }
                }

                // Logout
                IconButton(
                    onClick = onLogout,
                    modifier = Modifier.testTag("logout_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        tint = MagentaNeon
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = SurfaceDark
            )
        )
    }

    if (showNotifDialog) {
        AlertDialog(
            onDismissRequest = { showNotifDialog = false },
            title = {
                Text(
                    text = "System Telemetry Feed",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Status: Prototype Monitoring Active",
                        style = MaterialTheme.typography.bodySmall,
                        color = CyanAccent
                    )
                    notifications.forEach { notif ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceCardDark)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (notif.isAlert) Icons.Default.Warning else Icons.Default.Info,
                                contentDescription = null,
                                tint = if (notif.isAlert) MagentaNeon else CyanNeon,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = notif.title,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                                    color = TextPrimary
                                )
                                Text(
                                    text = notif.timestamp,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showNotifDialog = false }) {
                    Text("Close", color = CyanNeon)
                }
            },
            containerColor = SurfaceDark
        )
    }

    if (showSimMenu) {
        AlertDialog(
            onDismissRequest = { showSimMenu = false },
            title = {
                Text(
                    text = "Simulate Telemetry Event",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = TextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "This demonstrates that all counters, cards, and charts are driven entirely by central reactive application state.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )

                    FilledTonalButton(
                        onClick = {
                            onSimulateVehicle()
                            showSimMenu = false
                        },
                        modifier = Modifier.fillMaxWidth().testTag("simulate_vehicle_approach_button"),
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = SurfaceCardDark)
                    ) {
                        Icon(Icons.Default.Traffic, contentDescription = null, tint = CyanNeon)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("+1 Vehicle Approach Event", color = TextPrimary)
                    }

                    FilledTonalButton(
                        onClick = {
                            onSimulateSuspicious()
                            showSimMenu = false
                        },
                        modifier = Modifier.fillMaxWidth().testTag("simulate_suspicious_event_button"),
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = SurfaceCardDark)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = MagentaNeon)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("+1 Suspicious Vehicle Flag", color = TextPrimary)
                    }

                    FilledTonalButton(
                        onClick = {
                            onResetBaseline()
                            showSimMenu = false
                        },
                        modifier = Modifier.fillMaxWidth().testTag("reset_baseline_button"),
                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = SurfaceCardDark)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = TextMuted)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reset All Counters to 0", color = TextMuted)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSimMenu = false }) {
                    Text("Done", color = CyanNeon)
                }
            },
            containerColor = SurfaceDark
        )
    }
}

@Composable
fun SidebarContent(
    activeTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(SurfaceDark)
            .border(BorderStroke(1.dp, BorderDark))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Logo header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 28.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(com.example.ui.theme.GreenPrimary, com.example.ui.theme.GreenDark)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Traffic,
                        contentDescription = "Logo",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "E Parivahan V6",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            letterSpacing = 0.5.sp
                        ),
                        color = TextPrimary
                    )
                    Text(
                        text = "smart road for, safer life",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        ),
                        color = com.example.ui.theme.GreenAccent
                    )
                }
            }

            Text(
                text = "NAVIGATION",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                ),
                color = TextMuted,
                modifier = Modifier.padding(start = 12.dp, bottom = 10.dp)
            )

            // Nav items list
            NAV_ITEMS.forEach { item ->
                val isSelected = activeTab == item.tab
                val bgBrush = if (isSelected) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            CyanNeon.copy(alpha = 0.18f),
                            Color.Transparent
                        )
                    )
                } else Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(item.testTag)
                        .clip(RoundedCornerShape(12.dp))
                        .background(bgBrush)
                        .border(
                            BorderStroke(
                                1.dp,
                                if (isSelected) BorderCyanGlow else Color.Transparent
                            ),
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { onTabSelected(item.tab) }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) CyanNeon.copy(alpha = 0.2f)
                                else SurfaceCardDark
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) CyanNeon else TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp
                            ),
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                        Text(
                            text = item.subtitle,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = if (isSelected) CyanAccent else TextMuted
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }

        // Bottom status chip
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceCardDark)
                .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "System Status",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = TextSecondary
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(com.example.ui.theme.GreenActive)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Simulation Mode Active",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TextMuted
            )
        }
    }
}
