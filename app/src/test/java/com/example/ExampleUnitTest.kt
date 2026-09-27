package com.example

import com.example.model.AuthorisedPosition
import com.example.model.NAMED_VEHICLES_LIST
import com.example.model.UserRole
import com.example.viewmodel.AppScreen
import com.example.viewmodel.EParivahanViewModel
import com.example.viewmodel.MainTab
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    private lateinit var viewModel: EParivahanViewModel

    @Before
    fun setup() {
        viewModel = EParivahanViewModel()
    }

    @Test
    fun initialState_hasStrictZeroBaseline() {
        val state = viewModel.uiState.value
        assertEquals(0, state.vehicleStats.totalVehicles)
        assertEquals(0, state.vehicleStats.vehicleApproach)
        assertEquals(0, state.vehicleStats.fatalCasualties)
        assertEquals(0, state.vehicleStats.slightCasualties)
        assertEquals(0, state.vehicleStats.suspiciousVehicles)

        // Vehicle types
        assertEquals(0, state.vehicleTypeCounts.fourWheeler)
        assertEquals(0, state.vehicleTypeCounts.twoWheeler)
        assertEquals(0, state.vehicleTypeCounts.threeWheeler)
        assertEquals(0, state.vehicleTypeCounts.goodsCarrier)
        assertEquals(0, state.vehicleTypeCounts.publicTransport)

        // Fuel types
        assertEquals(0, state.fuelStats.petrol)
        assertEquals(0, state.fuelStats.diesel)
        assertEquals(0, state.fuelStats.electric)
        assertEquals(0, state.fuelStats.hybrid)

        // Database stats
        assertEquals(0, state.databaseStats.suspiciousRecordings)
        assertEquals(0, state.databaseStats.accidents)
        assertEquals(0, state.databaseStats.manualOperations)
    }

    @Test
    fun weatherForecast_hasSpecifiedValues() {
        val weather = viewModel.uiState.value.databaseStats.weatherForecast
        assertEquals("30°C", weather.temperature)
        assertEquals("60%", weather.humidity)
        assertEquals("10–12%", weather.fog)
        assertEquals("98%", weather.landslideChances)
        assertEquals("99%", weather.earthquakeChances)
    }

    @Test
    fun energySection_hasExactSpecifications() {
        val state = viewModel.uiState.value
        assertEquals(4.0, state.energyStats.totalConsumption, 0.01)
        assertEquals(10.0, state.energyStats.solarPower, 0.01)
        assertEquals(0, state.energyStats.supplyLoad)

        assertEquals(98, state.batteryStats.health)
        assertEquals(1200, state.batteryStats.capacity)
        assertEquals(4.82, state.batteryStats.connectedLoad, 0.01)
        assertEquals(39, state.batteryStats.temperature)

        assertEquals(34, state.moduleStats.cpu1Temperature)
        assertEquals(listOf("Camera", "AI Module"), state.moduleStats.cpu1Devices)
        assertEquals(30, state.moduleStats.cpu2Temperature)
        assertEquals(listOf("Weather Detector", "Ultrasonic Frequency Tracer"), state.moduleStats.cpu2Devices)
    }

    @Test
    fun loginValidation_emptyUsername_fails() {
        viewModel.setLoginUsername("")
        viewModel.setLoginPassword("password123")
        viewModel.performLogin()

        assertEquals("Username is required.", viewModel.uiState.value.loginState.errorMessage)
    }

    @Test
    fun loginValidation_emptyPassword_fails() {
        viewModel.setLoginUsername("admin")
        viewModel.setLoginPassword("")
        viewModel.performLogin()

        assertEquals("Password is required.", viewModel.uiState.value.loginState.errorMessage)
    }

    @Test
    fun loginValidation_authorisedPersonPosition_accessRestricted() {
        viewModel.setLoginRole(UserRole.AUTHORISED_PERSON)
        viewModel.setAuthorisedPosition(AuthorisedPosition.AUTHORISED_PERSON)
        viewModel.setLoginUsername("officer")
        viewModel.setLoginPassword("secret")
        viewModel.performLogin()

        assertEquals(
            "Access restricted in this prototype. Administrator authorization required.",
            viewModel.uiState.value.loginState.errorMessage
        )
    }

    @Test
    fun vehicleApproachInteraction_refreshesCountAndShowsVehicle() {
        assertEquals(0, viewModel.uiState.value.vehicleStats.vehicleApproach)

        // Open approach modal and click refresh
        viewModel.openApproachModal()
        assertTrue(viewModel.uiState.value.isApproachModalOpen)

        viewModel.refreshVehicleApproach()

        val updated = viewModel.uiState.value
        assertEquals(1, updated.vehicleStats.vehicleApproach)
        assertEquals(1, updated.vehicleStats.totalVehicles)
        assertEquals("MH19CK7154", updated.vehicleStats.detectedVehicleNumber)
        assertNotNull(updated.toastMessage)

        // Click vehicle to open details
        viewModel.openVehicleDetailsModal()
        assertTrue(viewModel.uiState.value.isVehicleDetailsModalOpen)
        assertEquals("NILKANTH BONDE", updated.vehicleDetails.ownerName)
        assertEquals("BAJAJ AUTO LTD.", updated.vehicleDetails.makerName)
        assertEquals("CT 100", updated.vehicleDetails.modelName)
        assertEquals("RTO JALGAON, MAHARASHTRA", updated.vehicleDetails.registrationAuthority)

        // See named vehicles
        viewModel.openNamedVehiclesModal()
        assertTrue(viewModel.uiState.value.isNamedVehiclesModalOpen)
        assertEquals(6, NAMED_VEHICLES_LIST.size)
        assertEquals("BAJAJ CT 100", NAMED_VEHICLES_LIST[0].name)
        assertEquals("MAHINDRA THAR ROXX", NAMED_VEHICLES_LIST[1].name)
        assertEquals("HONDA CB UNICORN", NAMED_VEHICLES_LIST[2].name)
        assertEquals("MAHINDRA SCORPIO N CLASSIC", NAMED_VEHICLES_LIST[3].name)
        assertEquals("FARMTRAC 45 TRACTOR", NAMED_VEHICLES_LIST[4].name)
        assertEquals("TATA SIERRA", NAMED_VEHICLES_LIST[5].name)
    }
}
