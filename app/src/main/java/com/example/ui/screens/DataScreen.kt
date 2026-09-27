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
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DatabaseStats
import com.example.ui.components.StatCard
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BorderDark
import com.example.ui.theme.BorderGreenGlow
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.GreenAccent
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.MagentaNeon
import com.example.ui.theme.SurfaceCardDark
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DataScreen(
    databaseStats: DatabaseStats,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .testTag("data_screen")
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
                    text = "E Parivahan V6",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GreenAccent,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "Stored Database",
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
                        title = "Suspicious Vehicle Recording",
                        value = "${databaseStats.suspiciousRecordings}×",
                        icon = Icons.Default.RemoveRedEye,
                        accentColor = MagentaNeon,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_db_suspicious"
                    )
                    StatCard(
                        title = "Accidents",
                        value = "${databaseStats.accidents}×",
                        icon = Icons.Default.CarCrash,
                        accentColor = MagentaNeon,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_db_accidents"
                    )
                    StatCard(
                        title = "Manual Operation",
                        value = "${databaseStats.manualOperations}×",
                        icon = Icons.Default.DisplaySettings,
                        accentColor = GreenPrimary,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_db_manual"
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Suspicious Vehicle Recording",
                        value = "${databaseStats.suspiciousRecordings}×",
                        icon = Icons.Default.RemoveRedEye,
                        accentColor = MagentaNeon,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "stat_db_suspicious"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            title = "Accidents",
                            value = "${databaseStats.accidents}×",
                            icon = Icons.Default.CarCrash,
                            accentColor = MagentaNeon,
                            modifier = Modifier.weight(1f),
                            testTag = "stat_db_accidents"
                        )
                        StatCard(
                            title = "Manual Operation",
                            value = "${databaseStats.manualOperations}×",
                            icon = Icons.Default.DisplaySettings,
                            accentColor = GreenPrimary,
                            modifier = Modifier.weight(1f),
                            testTag = "stat_db_manual"
                        )
                    }
                }
            }

            // Weather Forecast — CPU 2 Panel (5 equal sections)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("weather_forecast_card")
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
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(GreenPrimary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cloud,
                                    contentDescription = null,
                                    tint = GreenPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Weather Forecast — CPU 2",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 17.sp
                                    ),
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Environmental & geological anomaly monitoring unit",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = TextSecondary
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceDark)
                                .border(1.dp, BorderGreenGlow, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "CPU 2 LINKED",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = GreenPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    val weather = databaseStats.weatherForecast

                    // 5 Equal Information Sections
                    if (isWideScreen) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            WeatherInfoItem(
                                title = "Today's Temperature",
                                value = weather.temperature,
                                icon = Icons.Default.Thermostat,
                                color = AmberWarning,
                                modifier = Modifier.weight(1f)
                            )
                            WeatherInfoItem(
                                title = "Humidity",
                                value = weather.humidity,
                                icon = Icons.Default.WaterDrop,
                                color = CyanAccent,
                                modifier = Modifier.weight(1f)
                            )
                            WeatherInfoItem(
                                title = "Fog",
                                value = weather.fog,
                                icon = Icons.Default.CloudQueue,
                                color = TextSecondary,
                                modifier = Modifier.weight(1f)
                            )
                            WeatherInfoItem(
                                title = "Landslide Chances",
                                value = weather.landslideChances,
                                icon = Icons.Default.Landscape,
                                color = MagentaNeon,
                                modifier = Modifier.weight(1f)
                            )
                            WeatherInfoItem(
                                title = "Earthquake Chances",
                                value = weather.earthquakeChances,
                                icon = Icons.Default.Vibration,
                                color = MagentaNeon,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                WeatherInfoItem(
                                    title = "Today's Temperature",
                                    value = weather.temperature,
                                    icon = Icons.Default.Thermostat,
                                    color = AmberWarning,
                                    modifier = Modifier.weight(1f)
                                )
                                WeatherInfoItem(
                                    title = "Humidity",
                                    value = weather.humidity,
                                    icon = Icons.Default.WaterDrop,
                                    color = CyanAccent,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                WeatherInfoItem(
                                    title = "Fog",
                                    value = weather.fog,
                                    icon = Icons.Default.CloudQueue,
                                    color = TextSecondary,
                                    modifier = Modifier.weight(1f)
                                )
                                WeatherInfoItem(
                                    title = "Landslide Chances",
                                    value = weather.landslideChances,
                                    icon = Icons.Default.Landscape,
                                    color = MagentaNeon,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            WeatherInfoItem(
                                title = "Earthquake Chances",
                                value = weather.earthquakeChances,
                                icon = Icons.Default.Vibration,
                                color = MagentaNeon,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Simulated Demonstration Values Disclaimer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceDark)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "NOTICE: These values are simulated demonstration values, not live weather or geological predictions.",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = TextMuted,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherInfoItem(
    title: String,
    value: String,
    icon: ImageVector,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .border(BorderStroke(1.dp, BorderDark), RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = TextSecondary,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                ),
                color = color
            )
        }
    }
}
