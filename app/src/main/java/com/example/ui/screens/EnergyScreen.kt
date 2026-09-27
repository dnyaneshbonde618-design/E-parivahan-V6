package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.SolarPower
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BatteryStats
import com.example.model.EnergyStats
import com.example.model.ModuleStats
import com.example.ui.components.StatCard
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.GreenActive
import com.example.ui.theme.MagentaNeon
import com.example.ui.theme.SurfaceCardDark
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun EnergyScreen(
    energyStats: EnergyStats,
    batteryStats: BatteryStats,
    moduleStats: ModuleStats,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .testTag("energy_screen")
            .background(BackgroundDark)
    ) {
        val isWideScreen = maxWidth > 760.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header
            Column {
                Text(
                    text = "E-Parivahan V.6",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "Energy Management",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.2.sp,
                        fontSize = 24.sp
                    ),
                    color = TextPrimary
                )
            }

            // Top 3 Statistics Cards
            if (isWideScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Total Energy Consumption",
                        value = "${energyStats.totalConsumption.toInt()} MU",
                        icon = Icons.Default.Bolt,
                        accentColor = CyanNeon,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_energy_consumption"
                    )
                    StatCard(
                        title = "Green Power by Solar",
                        value = "${energyStats.solarPower.toInt()} MU",
                        icon = Icons.Default.SolarPower,
                        accentColor = GreenActive,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_solar_power"
                    )
                    StatCard(
                        title = "Energy Supply Load",
                        value = "${energyStats.supplyLoad}×",
                        icon = Icons.Default.Speed,
                        accentColor = MagentaNeon,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_supply_load"
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Total Energy Consumption",
                        value = "${energyStats.totalConsumption.toInt()} MU",
                        icon = Icons.Default.Bolt,
                        accentColor = CyanNeon,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "stat_energy_consumption"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            title = "Green Power by Solar",
                            value = "${energyStats.solarPower.toInt()} MU",
                            icon = Icons.Default.SolarPower,
                            accentColor = GreenActive,
                            modifier = Modifier.weight(1f),
                            testTag = "stat_solar_power"
                        )
                        StatCard(
                            title = "Energy Supply Load",
                            value = "${energyStats.supplyLoad}×",
                            icon = Icons.Default.Speed,
                            accentColor = MagentaNeon,
                            modifier = Modifier.weight(1f),
                            testTag = "stat_supply_load"
                        )
                    }
                }
            }

            // Battery Storage Full-Width Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("battery_storage_card")
                    .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCardDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(GreenActive.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BatteryChargingFull,
                                    contentDescription = null,
                                    tint = GreenActive,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Battery Storage",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp
                                    ),
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Auxiliary energy storage bank telemetry",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                    color = TextSecondary
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(GreenActive.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "ONLINE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = GreenActive
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 4 Metrics Grid
                    if (isWideScreen) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            BatteryMetricBox(
                                title = "Health",
                                value = "${batteryStats.health}%",
                                progress = batteryStats.health / 100f,
                                progressColor = GreenActive,
                                modifier = Modifier.weight(1f)
                            )
                            BatteryMetricBox(
                                title = "Capacity",
                                value = "${batteryStats.capacity} mAh",
                                progress = 0.85f,
                                progressColor = CyanNeon,
                                modifier = Modifier.weight(1f)
                            )
                            BatteryMetricBox(
                                title = "Load Connected",
                                value = "${batteryStats.connectedLoad} W",
                                progress = 0.48f,
                                progressColor = CyanAccent,
                                modifier = Modifier.weight(1f)
                            )
                            BatteryMetricBox(
                                title = "Temperature",
                                value = "${batteryStats.temperature}°C",
                                progress = batteryStats.temperature / 100f,
                                progressColor = if (batteryStats.temperature > 45) MagentaNeon else GreenActive,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                BatteryMetricBox(
                                    title = "Health",
                                    value = "${batteryStats.health}%",
                                    progress = batteryStats.health / 100f,
                                    progressColor = GreenActive,
                                    modifier = Modifier.weight(1f)
                                )
                                BatteryMetricBox(
                                    title = "Capacity",
                                    value = "${batteryStats.capacity} mAh",
                                    progress = 0.85f,
                                    progressColor = CyanNeon,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                BatteryMetricBox(
                                    title = "Load Connected",
                                    value = "${batteryStats.connectedLoad} W",
                                    progress = 0.48f,
                                    progressColor = CyanAccent,
                                    modifier = Modifier.weight(1f)
                                )
                                BatteryMetricBox(
                                    title = "Temperature",
                                    value = "${batteryStats.temperature}°C",
                                    progress = batteryStats.temperature / 100f,
                                    progressColor = if (batteryStats.temperature > 45) MagentaNeon else GreenActive,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            // Modules Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("modules_card")
                    .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceCardDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Modules",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                ),
                                color = TextPrimary
                            )
                            Text(
                                text = "Dual core processing & peripheral units",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = TextSecondary
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(GreenActive)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ACTIVE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = GreenActive
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Two Equal Sections: MODULE 1 and MODULE 2
                    if (isWideScreen) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ModuleCardItem(
                                moduleName = "MODULE 1",
                                cpuName = "CPU 1",
                                temperature = "${moduleStats.cpu1Temperature}°C",
                                devices = moduleStats.cpu1Devices,
                                modifier = Modifier.weight(1f),
                                testTag = "module_1_section"
                            )
                            ModuleCardItem(
                                moduleName = "MODULE 2",
                                cpuName = "CPU 2",
                                temperature = "${moduleStats.cpu2Temperature}°C",
                                devices = moduleStats.cpu2Devices,
                                modifier = Modifier.weight(1f),
                                testTag = "module_2_section"
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ModuleCardItem(
                                moduleName = "MODULE 1",
                                cpuName = "CPU 1",
                                temperature = "${moduleStats.cpu1Temperature}°C",
                                devices = moduleStats.cpu1Devices,
                                modifier = Modifier.fillMaxWidth(),
                                testTag = "module_1_section"
                            )
                            ModuleCardItem(
                                moduleName = "MODULE 2",
                                cpuName = "CPU 2",
                                temperature = "${moduleStats.cpu2Temperature}°C",
                                devices = moduleStats.cpu2Devices,
                                modifier = Modifier.fillMaxWidth(),
                                testTag = "module_2_section"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BatteryMetricBox(
    title: String,
    value: String,
    progress: Float,
    progressColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Column {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                    letterSpacing = 0.8.sp
                ),
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = progressColor,
                trackColor = BorderDark
            )
        }
    }
}

@Composable
fun ModuleCardItem(
    moduleName: String,
    cpuName: String,
    temperature: String,
    devices: List<String>,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Card(
        modifier = modifier
            .testTag(testTag)
            .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(CyanNeon.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = null,
                            tint = CyanNeon,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = moduleName,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp
                            ),
                            color = CyanAccent
                        )
                        Text(
                            text = cpuName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = TextPrimary
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Thermostat,
                        contentDescription = "Temperature",
                        tint = GreenActive,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = temperature,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "CONNECTED DEVICES",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                ),
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                devices.forEach { deviceName ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceCardDark)
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(GreenActive)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = deviceName,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            ),
                            color = TextPrimary
                        )
                    }
                }
            }
        }
    }
}
