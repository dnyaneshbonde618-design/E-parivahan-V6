package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FuelStats
import com.example.model.TimePoint
import com.example.model.VehicleStats
import com.example.model.VehicleTypeCounts
import com.example.ui.components.StatCard
import com.example.ui.components.VehicleCountByTypeSection
import com.example.ui.components.VehicleCountFuelTypeCard
import com.example.ui.components.VehicleCountTimeSeriesChart
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BorderDark
import com.example.ui.theme.BorderGreenGlow
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.GreenAccent
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.MagentaNeon
import com.example.ui.theme.SurfaceCardDark
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DashboardScreen(
    vehicleStats: VehicleStats,
    vehicleTypeCounts: VehicleTypeCounts,
    timeSeriesData: List<TimePoint>,
    fuelStats: FuelStats,
    onOpenApproachModal: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .testTag("dashboard_screen")
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
            // Dashboard Header Banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        text = "DASHBOARD",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.2.sp,
                            fontSize = 24.sp
                        ),
                        color = TextPrimary
                    )
                }

                // Upper right stat: Total Vehicle = 0
                Box(
                    modifier = Modifier
                        .testTag("total_vehicle_header_badge")
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceCardDark)
                        .border(1.2.dp, BorderGreenGlow, RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Total Vehicle = ${vehicleStats.totalVehicles}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        color = GreenPrimary
                    )
                }
            }

            // Top 4 Statistics Cards
            // Card 1 "Total Vehicle Approach" is CLICKABLE!
            if (isWideScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Total Vehicle Approach",
                        value = "${vehicleStats.vehicleApproach}",
                        icon = Icons.Default.Traffic,
                        accentColor = GreenPrimary,
                        subtitle = "Tap to open approach panel ↗",
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onOpenApproachModal() },
                        testTag = "stat_vehicle_approach"
                    )
                    StatCard(
                        title = "Total Fatal Casualties",
                        value = "${vehicleStats.fatalCasualties}×",
                        icon = Icons.Default.CarCrash,
                        accentColor = MagentaNeon,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_fatal_casualties"
                    )
                    StatCard(
                        title = "Slight Casualties / Emergency Service Needed",
                        value = "${vehicleStats.slightCasualties}×",
                        icon = Icons.Default.Emergency,
                        accentColor = AmberWarning,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_slight_casualties"
                    )
                    StatCard(
                        title = "Suspicious Vehicle",
                        value = "${vehicleStats.suspiciousVehicles}×",
                        icon = Icons.Default.RemoveRedEye,
                        accentColor = MagentaNeon,
                        modifier = Modifier.weight(1f),
                        testTag = "stat_suspicious_vehicle"
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Total Vehicle Approach",
                        value = "${vehicleStats.vehicleApproach}",
                        icon = Icons.Default.Traffic,
                        accentColor = GreenPrimary,
                        subtitle = "Tap to open approach panel ↗",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenApproachModal() },
                        testTag = "stat_vehicle_approach"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            title = "Total Fatal Casualties",
                            value = "${vehicleStats.fatalCasualties}×",
                            icon = Icons.Default.CarCrash,
                            accentColor = MagentaNeon,
                            modifier = Modifier.weight(1f),
                            testTag = "stat_fatal_casualties"
                        )
                        StatCard(
                            title = "Suspicious Vehicle",
                            value = "${vehicleStats.suspiciousVehicles}×",
                            icon = Icons.Default.RemoveRedEye,
                            accentColor = MagentaNeon,
                            modifier = Modifier.weight(1f),
                            testTag = "stat_suspicious_vehicle"
                        )
                    }
                    StatCard(
                        title = "Slight Casualties / Emergency Service Needed",
                        value = "${vehicleStats.slightCasualties}×",
                        icon = Icons.Default.Emergency,
                        accentColor = AmberWarning,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "stat_slight_casualties"
                    )
                }
            }

            // Vehicle Count by Type & Charts
            if (isWideScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    VehicleCountByTypeSection(
                        counts = vehicleTypeCounts,
                        modifier = Modifier.weight(1f)
                    )
                    Column(
                        modifier = Modifier.weight(1.3f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        VehicleCountTimeSeriesChart(data = timeSeriesData)
                        VehicleCountFuelTypeCard(fuelStats = fuelStats)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    VehicleCountByTypeSection(
                        counts = vehicleTypeCounts,
                        modifier = Modifier.fillMaxWidth()
                    )
                    VehicleCountTimeSeriesChart(
                        data = timeSeriesData,
                        modifier = Modifier.fillMaxWidth()
                    )
                    VehicleCountFuelTypeCard(
                        fuelStats = fuelStats,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Student Prototype Notice
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceCardDark.copy(alpha = 0.5f))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Student Prototype • E Parivahan V6 • Tap 'Total Vehicle Approach' to test detection",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = TextSecondary
                )
            }
        }
    }
}
