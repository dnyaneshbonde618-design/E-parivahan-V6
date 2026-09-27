package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NAMED_VEHICLES_LIST
import com.example.ui.components.CommandCenterTopBar
import com.example.ui.components.NamedVehiclesDialog
import com.example.ui.components.SidebarContent
import com.example.ui.components.VehicleApproachDialog
import com.example.ui.components.VehicleDetailsDialog
import com.example.ui.screens.CreditScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DataScreen
import com.example.ui.screens.EnergyScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.BorderGreenGlow
import com.example.ui.theme.GreenPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SurfaceCardDark
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextPrimary
import com.example.viewmodel.AppScreen
import com.example.viewmodel.EParivahanViewModel
import com.example.viewmodel.MainTab
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                EParivahanApp()
            }
        }
    }
}

@Composable
fun EParivahanApp(
    viewModel: EParivahanViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Auto-dismiss toast
    LaunchedEffect(uiState.toastMessage) {
        if (uiState.toastMessage != null) {
            delay(3000)
            viewModel.clearToast()
        }
    }

    when (uiState.currentScreen) {
        AppScreen.SPLASH -> {
            SplashScreen(
                onContinue = { viewModel.onSplashContinue() }
            )
        }

        AppScreen.LOGIN -> {
            LoginScreen(
                state = uiState.loginState,
                onRoleSelected = { viewModel.setLoginRole(it) },
                onUsernameChange = { viewModel.setLoginUsername(it) },
                onPasswordChange = { viewModel.setLoginPassword(it) },
                onPositionSelected = { viewModel.setAuthorisedPosition(it) },
                onLoginClick = { viewModel.performLogin() }
            )
        }

        AppScreen.MAIN -> {
            // Android Back button handling
            BackHandler(enabled = true) {
                if (uiState.isNamedVehiclesModalOpen) {
                    viewModel.closeNamedVehiclesModal()
                } else if (uiState.isVehicleDetailsModalOpen) {
                    viewModel.closeVehicleDetailsModal()
                } else if (uiState.isApproachModalOpen) {
                    viewModel.closeApproachModal()
                } else if (drawerState.isOpen) {
                    coroutineScope.launch { drawerState.close() }
                } else if (uiState.activeTab != MainTab.HOME) {
                    viewModel.selectTab(MainTab.HOME)
                } else {
                    viewModel.logout()
                }
            }

            BoxWithConstraints(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {
                val isDesktopOrTablet = maxWidth >= 840.dp

                if (isDesktopOrTablet) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        SidebarContent(
                            activeTab = uiState.activeTab,
                            onTabSelected = { viewModel.selectTab(it) }
                        )

                        Scaffold(
                            contentWindowInsets = WindowInsets.systemBars,
                            topBar = {
                                CommandCenterTopBar(
                                    activeTab = uiState.activeTab,
                                    loggedInUser = uiState.loggedInUser,
                                    loggedInRole = uiState.loggedInRole,
                                    notifications = emptyList<com.example.model.SystemNotification>(),
                                    onOpenDrawer = {},
                                    onSimulateVehicle = { viewModel.refreshVehicleApproach() },
                                    onSimulateSuspicious = {},
                                    onResetBaseline = {},
                                    onLogout = { viewModel.logout() }
                                )
                            },
                            containerColor = BackgroundDark
                        ) { innerPadding ->
                            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                                MainTabContent(
                                    activeTab = uiState.activeTab,
                                    uiState = uiState,
                                    onOpenApproachModal = { viewModel.openApproachModal() }
                                )
                            }
                        }
                    }
                } else {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet(
                                drawerContainerColor = SurfaceDark
                            ) {
                                SidebarContent(
                                    activeTab = uiState.activeTab,
                                    onTabSelected = {
                                        viewModel.selectTab(it)
                                        coroutineScope.launch { drawerState.close() }
                                    },
                                    modifier = Modifier.fillMaxHeight()
                                )
                            }
                        }
                    ) {
                        Scaffold(
                            contentWindowInsets = WindowInsets.systemBars,
                            topBar = {
                                CommandCenterTopBar(
                                    activeTab = uiState.activeTab,
                                    loggedInUser = uiState.loggedInUser,
                                    loggedInRole = uiState.loggedInRole,
                                    notifications = emptyList<com.example.model.SystemNotification>(),
                                    onOpenDrawer = {
                                        coroutineScope.launch { drawerState.open() }
                                    },
                                    onSimulateVehicle = { viewModel.refreshVehicleApproach() },
                                    onSimulateSuspicious = {},
                                    onResetBaseline = {},
                                    onLogout = { viewModel.logout() }
                                )
                            },
                            containerColor = BackgroundDark
                        ) { innerPadding ->
                            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                                MainTabContent(
                                    activeTab = uiState.activeTab,
                                    uiState = uiState,
                                    onOpenApproachModal = { viewModel.openApproachModal() }
                                )
                            }
                        }
                    }
                }

                // Toast Notification Banner
                AnimatedVisibility(
                    visible = uiState.toastMessage != null,
                    enter = slideInVertically { -it } + fadeIn(),
                    exit = slideOutVertically { -it } + fadeOut(),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 70.dp, start = 16.dp, end = 16.dp)
                ) {
                    if (uiState.toastMessage != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceCardDark)
                                .border(BorderStroke(1.2.dp, BorderGreenGlow), RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = GreenPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = uiState.toastMessage!!,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    ),
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }

                // Modals
                if (uiState.isApproachModalOpen) {
                    VehicleApproachDialog(
                        vehicleStats = uiState.vehicleStats,
                        onRefresh = { viewModel.refreshVehicleApproach() },
                        onVehicleClick = { viewModel.openVehicleDetailsModal() },
                        onDismiss = { viewModel.closeApproachModal() }
                    )
                }

                if (uiState.isVehicleDetailsModalOpen) {
                    VehicleDetailsDialog(
                        record = uiState.vehicleDetails,
                        onSeeNamedVehicle = { viewModel.openNamedVehiclesModal() },
                        onDismiss = { viewModel.closeVehicleDetailsModal() }
                    )
                }

                if (uiState.isNamedVehiclesModalOpen) {
                    NamedVehiclesDialog(
                        vehicles = NAMED_VEHICLES_LIST,
                        selectedVehicle = uiState.selectedNamedVehicle,
                        onSelectVehicle = { viewModel.selectNamedVehicle(it) },
                        onDismiss = { viewModel.closeNamedVehiclesModal() }
                    )
                }
            }
        }
    }
}

@Composable
private fun MainTabContent(
    activeTab: MainTab,
    uiState: com.example.viewmodel.EParivahanUiState,
    onOpenApproachModal: () -> Unit
) {
    when (activeTab) {
        MainTab.HOME -> DashboardScreen(
            vehicleStats = uiState.vehicleStats,
            vehicleTypeCounts = uiState.vehicleTypeCounts,
            timeSeriesData = uiState.timeSeriesData,
            fuelStats = uiState.fuelStats,
            onOpenApproachModal = onOpenApproachModal
        )
        MainTab.ENERGY -> EnergyScreen(
            energyStats = uiState.energyStats,
            batteryStats = uiState.batteryStats,
            moduleStats = uiState.moduleStats
        )
        MainTab.DATA -> DataScreen(
            databaseStats = uiState.databaseStats
        )
        MainTab.CREDIT -> CreditScreen()
    }
}
