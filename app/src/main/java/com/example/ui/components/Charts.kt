package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.EvStation
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Timeline
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FuelStats
import com.example.model.TimePoint
import com.example.ui.theme.BorderDark
import com.example.ui.theme.ChartFillCyan
import com.example.ui.theme.ChartLineCyan
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.FuelDiesel
import com.example.ui.theme.FuelElectric
import com.example.ui.theme.FuelHybrid
import com.example.ui.theme.FuelPetrol
import com.example.ui.theme.SurfaceCardDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.math.max

@Composable
fun VehicleCountTimeSeriesChart(
    data: List<TimePoint>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .testTag("vehicle_count_by_time_chart")
            .border(
                BorderStroke(1.dp, BorderDark),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCardDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Vehicle Count by Day & Time",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        color = TextPrimary
                    )
                    Text(
                        text = "Real-time road monitoring timeline (24hr cycle)",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextSecondary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(CyanNeon.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = "Timeline",
                        tint = CyanNeon,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Canvas Chart
            val maxCount = max(data.maxOfOrNull { it.count } ?: 0, 5)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                    val w = size.width
                    val h = size.height - 24.dp.toPx() // leave space for bottom axis
                    val topPadding = 12.dp.toPx()
                    val chartHeight = h - topPadding

                    // Draw 4 horizontal gridlines
                    for (i in 0..3) {
                        val y = topPadding + chartHeight * (i / 3f)
                        drawLine(
                            color = Color(0xFF1E293B),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    val points = data.mapIndexed { index, timePoint ->
                        val x = if (data.size > 1) {
                            (index.toFloat() / (data.size - 1)) * w
                        } else w / 2f
                        val normalizedVal = timePoint.count.toFloat() / maxCount.toFloat()
                        val y = (topPadding + chartHeight) - (normalizedVal * chartHeight)
                        Offset(x, y)
                    }

                    if (points.isNotEmpty()) {
                        // Area fill path
                        val fillPath = Path().apply {
                            moveTo(points.first().x, topPadding + chartHeight)
                            points.forEach { lineTo(it.x, it.y) }
                            lineTo(points.last().x, topPadding + chartHeight)
                            close()
                        }
                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    ChartFillCyan.copy(alpha = 0.35f),
                                    Color.Transparent
                                ),
                                startY = topPadding,
                                endY = topPadding + chartHeight
                            )
                        )

                        // Line stroke path
                        val linePath = Path().apply {
                            moveTo(points.first().x, points.first().y)
                            points.forEach { lineTo(it.x, it.y) }
                        }
                        drawPath(
                            path = linePath,
                            color = ChartLineCyan,
                            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Draw point circles
                        points.forEach { pt ->
                            drawCircle(
                                color = Color(0xFF030712),
                                radius = 4.5.dp.toPx(),
                                center = pt
                            )
                            drawCircle(
                                color = ChartLineCyan,
                                radius = 3.dp.toPx(),
                                center = pt
                            )
                        }
                    }
                }
            }

            // X-axis Time Labels
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                data.forEach { tp ->
                    Text(
                        text = tp.timeLabel,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Composable
fun VehicleCountFuelTypeCard(
    fuelStats: FuelStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .testTag("vehicle_count_by_fuel_type_card")
            .border(
                BorderStroke(1.dp, BorderDark),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCardDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "Vehicle Count by Fuel Type",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = TextPrimary
            )
            Text(
                text = "Eco & energy classification breakdown",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Row with Donut Gauge and List
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Donut Chart on Left
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val totalFuel = fuelStats.petrol + fuelStats.diesel + fuelStats.electric + fuelStats.hybrid
                    Canvas(modifier = Modifier.size(100.dp)) {
                        val strokeWidth = 12.dp.toPx()
                        val arcSize = size.width - strokeWidth
                        val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)

                        // Baseline background circle
                        drawArc(
                            color = Color(0xFF1E293B),
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = Size(arcSize, arcSize),
                            style = Stroke(width = strokeWidth)
                        )

                        if (totalFuel > 0) {
                            var currentAngle = -90f
                            val pSweep = (fuelStats.petrol.toFloat() / totalFuel) * 360f
                            val dSweep = (fuelStats.diesel.toFloat() / totalFuel) * 360f
                            val eSweep = (fuelStats.electric.toFloat() / totalFuel) * 360f
                            val hSweep = (fuelStats.hybrid.toFloat() / totalFuel) * 360f

                            if (pSweep > 0) {
                                drawArc(
                                    color = FuelPetrol,
                                    startAngle = currentAngle,
                                    sweepAngle = pSweep,
                                    useCenter = false,
                                    topLeft = topLeft,
                                    size = Size(arcSize, arcSize),
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                                )
                                currentAngle += pSweep
                            }
                            if (dSweep > 0) {
                                drawArc(
                                    color = FuelDiesel,
                                    startAngle = currentAngle,
                                    sweepAngle = dSweep,
                                    useCenter = false,
                                    topLeft = topLeft,
                                    size = Size(arcSize, arcSize),
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                                )
                                currentAngle += dSweep
                            }
                            if (eSweep > 0) {
                                drawArc(
                                    color = FuelElectric,
                                    startAngle = currentAngle,
                                    sweepAngle = eSweep,
                                    useCenter = false,
                                    topLeft = topLeft,
                                    size = Size(arcSize, arcSize),
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                                )
                                currentAngle += eSweep
                            }
                            if (hSweep > 0) {
                                drawArc(
                                    color = FuelHybrid,
                                    startAngle = currentAngle,
                                    sweepAngle = hSweep,
                                    useCenter = false,
                                    topLeft = topLeft,
                                    size = Size(arcSize, arcSize),
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                                )
                            }
                        } else {
                            // Subdued zero state indicator ring
                            drawArc(
                                color = CyanNeon.copy(alpha = 0.25f),
                                startAngle = -90f,
                                sweepAngle = 360f,
                                useCenter = false,
                                topLeft = topLeft,
                                size = Size(arcSize, arcSize),
                                style = Stroke(width = strokeWidth)
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${fuelStats.petrol + fuelStats.diesel + fuelStats.electric + fuelStats.hybrid}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = TextPrimary
                        )
                        Text(
                            text = "TOTAL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                letterSpacing = 1.sp
                            ),
                            color = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Breakdown list on Right
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FuelMetricRow("Petrol", "${fuelStats.petrol}×", FuelPetrol, Icons.Default.LocalGasStation)
                    FuelMetricRow("Diesel", "${fuelStats.diesel}×", FuelDiesel, Icons.Default.EvStation)
                    FuelMetricRow("EV / Electric Vehicle", "${fuelStats.electric}×", FuelElectric, Icons.Default.ElectricCar)
                    FuelMetricRow("Hybrid Vehicle", "${fuelStats.hybrid}×", FuelHybrid, Icons.Default.ElectricCar)
                }
            }
        }
    }
}

@Composable
private fun FuelMetricRow(
    label: String,
    value: String,
    color: Color,
    icon: ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                ),
                color = TextSecondary
            )
        }

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            ),
            color = TextPrimary
        )
    }
}
