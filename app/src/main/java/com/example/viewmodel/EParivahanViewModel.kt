package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthorisedPosition
import com.example.model.BatteryStats
import com.example.model.DatabaseStats
import com.example.model.EnergyStats
import com.example.model.FuelStats
import com.example.model.ModuleStats
import com.example.model.NamedVehicle
import com.example.model.TimePoint
import com.example.model.UserRole
import com.example.model.VehicleDetailsRecord
import com.example.model.VehicleStats
import com.example.model.VehicleTypeCounts
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppScreen {
    SPLASH,
    LOGIN,
    MAIN
}

enum class MainTab {
    HOME,
    ENERGY,
    DATA,
    CREDIT
}

data class LoginUiState(
    val selectedRole: UserRole = UserRole.GENERAL_USER,
    val username: String = "",
    val password: String = "",
    val selectedPosition: AuthorisedPosition? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val infoMessage: String? = null
)

data class EParivahanUiState(
    val currentScreen: AppScreen = AppScreen.SPLASH,
    val activeTab: MainTab = MainTab.HOME,
    val loggedInUser: String = "Guest",
    val loggedInRole: UserRole = UserRole.GENERAL_USER,
    val loggedInPosition: AuthorisedPosition? = null,
    val loginState: LoginUiState = LoginUiState(),
    val vehicleStats: VehicleStats = VehicleStats(),
    val vehicleTypeCounts: VehicleTypeCounts = VehicleTypeCounts(),
    val timeSeriesData: List<TimePoint> = listOf(
        TimePoint("00:00", 0),
        TimePoint("04:00", 0),
        TimePoint("08:00", 0),
        TimePoint("12:00", 0),
        TimePoint("16:00", 0),
        TimePoint("20:00", 0),
        TimePoint("24:00", 0)
    ),
    val fuelStats: FuelStats = FuelStats(),
    val energyStats: EnergyStats = EnergyStats(),
    val batteryStats: BatteryStats = BatteryStats(),
    val moduleStats: ModuleStats = ModuleStats(),
    val databaseStats: DatabaseStats = DatabaseStats(),
    val vehicleDetails: VehicleDetailsRecord = VehicleDetailsRecord(),
    // Modal states
    val isApproachModalOpen: Boolean = false,
    val isVehicleDetailsModalOpen: Boolean = false,
    val isNamedVehiclesModalOpen: Boolean = false,
    val selectedNamedVehicle: NamedVehicle? = null,
    val toastMessage: String? = null
)

class EParivahanViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EParivahanUiState())
    val uiState: StateFlow<EParivahanUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2200)
            if (_uiState.value.currentScreen == AppScreen.SPLASH) {
                _uiState.update { it.copy(currentScreen = AppScreen.LOGIN) }
            }
        }
    }

    fun onSplashContinue() {
        _uiState.update { it.copy(currentScreen = AppScreen.LOGIN) }
    }

    fun setLoginRole(role: UserRole) {
        _uiState.update {
            it.copy(
                loginState = it.loginState.copy(
                    selectedRole = role,
                    errorMessage = null,
                    infoMessage = null
                )
            )
        }
    }

    fun setLoginUsername(username: String) {
        _uiState.update {
            it.copy(loginState = it.loginState.copy(username = username, errorMessage = null))
        }
    }

    fun setLoginPassword(password: String) {
        _uiState.update {
            it.copy(loginState = it.loginState.copy(password = password, errorMessage = null))
        }
    }

    fun setAuthorisedPosition(position: AuthorisedPosition) {
        _uiState.update {
            it.copy(loginState = it.loginState.copy(selectedPosition = position, errorMessage = null))
        }
    }

    fun performLogin() {
        val state = _uiState.value.loginState
        val trimmedUser = state.username.trim()
        val trimmedPass = state.password.trim()

        if (trimmedUser.isEmpty()) {
            _uiState.update {
                it.copy(loginState = it.loginState.copy(errorMessage = "Username is required."))
            }
            return
        }

        if (trimmedPass.isEmpty()) {
            _uiState.update {
                it.copy(loginState = it.loginState.copy(errorMessage = "Password is required."))
            }
            return
        }

        if (state.selectedRole == UserRole.AUTHORISED_PERSON) {
            if (state.selectedPosition == null) {
                _uiState.update {
                    it.copy(loginState = it.loginState.copy(errorMessage = "Please select an authorised position."))
                }
                return
            }

            if (state.selectedPosition == AuthorisedPosition.AUTHORISED_PERSON) {
                _uiState.update {
                    it.copy(
                        loginState = it.loginState.copy(
                            errorMessage = "Access restricted in this prototype. Administrator authorization required."
                        )
                    )
                }
                return
            }
        }

        _uiState.update { it.copy(loginState = it.loginState.copy(isLoading = true, errorMessage = null)) }

        viewModelScope.launch {
            delay(700)
            _uiState.update {
                it.copy(
                    currentScreen = AppScreen.MAIN,
                    loggedInUser = trimmedUser,
                    loggedInRole = state.selectedRole,
                    loggedInPosition = state.selectedPosition,
                    loginState = it.loginState.copy(isLoading = false)
                )
            }
        }
    }

    fun selectTab(tab: MainTab) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun logout() {
        _uiState.update {
            it.copy(
                currentScreen = AppScreen.LOGIN,
                activeTab = MainTab.HOME,
                loginState = LoginUiState()
            )
        }
    }

    // Modal Controls
    fun openApproachModal() {
        _uiState.update { it.copy(isApproachModalOpen = true) }
    }

    fun closeApproachModal() {
        _uiState.update { it.copy(isApproachModalOpen = false) }
    }

    fun refreshVehicleApproach() {
        _uiState.update { current ->
            // Change value 0 -> 1 as specified in prompt
            val newApproach = 1
            val newTotal = 1
            current.copy(
                vehicleStats = current.vehicleStats.copy(
                    totalVehicles = newTotal,
                    vehicleApproach = newApproach,
                    isVehicleApproachRefreshed = true,
                    detectedVehicleNumber = "MH19CK7154"
                ),
                toastMessage = "Vehicle detection refreshed • 1 vehicle detected"
            )
        }
    }

    fun openVehicleDetailsModal() {
        _uiState.update {
            it.copy(
                isVehicleDetailsModalOpen = true,
                isApproachModalOpen = false // Dismiss approach modal
            )
        }
    }

    fun closeVehicleDetailsModal() {
        _uiState.update { it.copy(isVehicleDetailsModalOpen = false) }
    }

    fun openNamedVehiclesModal() {
        _uiState.update {
            it.copy(
                isNamedVehiclesModalOpen = true,
                isVehicleDetailsModalOpen = false
            )
        }
    }

    fun closeNamedVehiclesModal() {
        _uiState.update { it.copy(isNamedVehiclesModalOpen = false) }
    }

    fun selectNamedVehicle(vehicle: NamedVehicle?) {
        _uiState.update { it.copy(selectedNamedVehicle = vehicle) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
