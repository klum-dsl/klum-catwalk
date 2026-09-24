package onboarding.smarthome.api

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Key
import com.blackbuild.klum.ast.Required
import com.blackbuild.klum.ast.layer3.AutoCreate
import com.blackbuild.klum.ast.layer3.Cluster

@DSL
abstract class Home {

    @Key String name
    @AutoCreate @Cluster Map<String, Room> rooms
}

@DSL
abstract class Room {

    String displayName
    @AutoCreate @Cluster Map<String, Window> windows
    List<Device> devices
}

@DSL
abstract class HeatedRoom extends Room {

    @Required Thermostat thermostat
}

@DSL
abstract class Window {

    String displayName
    WindowSensor windowSensor
}

@DSL
abstract class Device { }

@DSL
class GenericDevice extends Device {

    String deviceId
    String kind
    String label
}

@DSL
abstract class Thermostat extends Device {

    BigDecimal targetTemperature
}

@DSL
class TadoThermostat extends Thermostat {

    String deviceId
}

@DSL
class HomematicThermostat extends Thermostat {

    int channel
    String serialNumber
}

@DSL
abstract class WindowSensor extends Device { }

@DSL
class HomematicWindowSensor extends WindowSensor {

    int channel
    String serialNumber
}

@DSL
abstract class SmokeDetector extends Device { }

@DSL
class HomematicSmokeDetector extends SmokeDetector {

    String serialNumber
}
