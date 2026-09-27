package com.example.model

enum class UserRole(val label: String) {
    GENERAL_USER("LOGIN AS GENERAL USER"),
    TSP("LOGIN AS T.S.P"),
    AUTHORISED_PERSON("LOGIN AS AUTHORISED PERSON")
}

enum class AuthorisedPosition(val label: String) {
    ADMINISTRATOR("Administrator"),
    AUTHORISED_PERSON("Authorised Person")
}

data class VehicleStats(
    val totalVehicles: Int = 0,
    val vehicleApproach: Int = 0,
    val fatalCasualties: Int = 0,
    val slightCasualties: Int = 0,
    val suspiciousVehicles: Int = 0,
    val isVehicleApproachRefreshed: Boolean = false,
    val detectedVehicleNumber: String? = null
)

data class VehicleTypeCounts(
    val fourWheeler: Int = 0,
    val twoWheeler: Int = 0,
    val threeWheeler: Int = 0,
    val goodsCarrier: Int = 0,
    val publicTransport: Int = 0
)

data class TimePoint(
    val timeLabel: String,
    val count: Int
)

data class FuelStats(
    val petrol: Int = 0,
    val diesel: Int = 0,
    val electric: Int = 0,
    val hybrid: Int = 0
)

data class EnergyStats(
    val totalConsumption: Double = 4.0, // 4 MU
    val solarPower: Double = 10.0,      // 10 MU
    val supplyLoad: Int = 0             // 0x
)

data class BatteryStats(
    val health: Int = 98,              // 98%
    val capacity: Int = 1200,          // 1200 mAh
    val connectedLoad: Double = 4.82,  // 4.82 W
    val temperature: Int = 39          // 39°C
)

data class ModuleStats(
    val cpu1Temperature: Int = 34,
    val cpu1Devices: List<String> = listOf("Camera", "AI Module"),
    val cpu2Temperature: Int = 30,
    val cpu2Devices: List<String> = listOf("Weather Detector", "Ultrasonic Frequency Tracer")
)

data class WeatherForecastData(
    val temperature: String = "30°C",
    val humidity: String = "60%",
    val fog: String = "10–12%",
    val landslideChances: String = "98%",
    val earthquakeChances: String = "99%"
)

data class DatabaseStats(
    val suspiciousRecordings: Int = 0,
    val accidents: Int = 0,
    val manualOperations: Int = 0,
    val weatherForecast: WeatherForecastData = WeatherForecastData()
)

data class VehicleDetailsRecord(
    val vehicleNumber: String = "MH19CK7154",
    val ownerName: String = "NILKANTH BONDE",
    val sonDaughterWifeOf: String = "W***N BONDE",
    val address: String = "A/P KALMODA, POST KHIRODA, TAL RAVER, DIST JALGAON",
    val mobileNo: String = "8698562063",
    val ownership: String = "Individual",
    val chassisNo: String = "MD2A18********157**",
    val engineNo: String = "DUZEFH84**",
    val hsrpFitted: String = "Yes",
    val makerName: String = "BAJAJ AUTO LTD.",
    val modelName: String = "CT 100",
    val registrationDate: String = "4-Apr-2016",
    val vehicleClass: String = "2 Wn",
    val fuelType: String = "Petrol",
    val color: String = "BLK RED",
    val seatCapacity: String = "2",
    val financier: String = "N/A",
    val insuranceCompany: String = "BAJAJ General Insurance Co. Ltd.",
    val insuranceValidity: String = "18-Feb-2017",
    val fitnessValidity: String = "3-Apr-2031",
    val puccNo: String = "N/A",
    val puccValidity: String = "N/A",
    val registrationAuthority: String = "RTO JALGAON, MAHARASHTRA"
)

data class SystemNotification(
    val id: String,
    val title: String,
    val timestamp: String,
    val isAlert: Boolean = false
)

data class NamedVehicle(
    val id: String,
    val name: String,
    val category: String,
    val fuel: String,
    val status: String,
    val description: String
)

val NAMED_VEHICLES_LIST = listOf(
    NamedVehicle("1", "BAJAJ CT 100", "Two Wheeler", "Petrol", "Primary Record", "Detected commuter two-wheeler, registered at RTO Jalgaon."),
    NamedVehicle("2", "MAHINDRA THAR ROXX", "Four Wheeler", "Diesel", "Logged Vehicle", "Four-wheel off-road utility vehicle with multi-terrain telemetry."),
    NamedVehicle("3", "HONDA CB UNICORN", "Two Wheeler", "Petrol", "Logged Vehicle", "Commuter two-wheeler with mono-suspension road telemetry."),
    NamedVehicle("4", "MAHINDRA SCORPIO N CLASSIC", "Four Wheeler", "Diesel", "Logged Vehicle", "Robust passenger SUV with electronic stability assistance."),
    NamedVehicle("5", "FARMTRAC 45 TRACTOR", "Goods Carrier", "Diesel", "Logged Vehicle", "Agricultural prime mover & regional haulage carrier."),
    NamedVehicle("6", "TATA SIERRA", "Four Wheeler", "E-Vehicle", "Logged Vehicle", "Next-gen concept electric transport with smart highway radar.")
)
