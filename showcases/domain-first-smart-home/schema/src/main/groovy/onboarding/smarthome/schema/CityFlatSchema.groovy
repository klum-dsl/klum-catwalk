package onboarding.smarthome.schema

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Required
import onboarding.smarthome.api.HeatedRoom
import onboarding.smarthome.api.Home
import onboarding.smarthome.api.SmokeDetector
import onboarding.smarthome.api.Window

@DSL
class CityFlat extends Home {

    Kitchen kitchen
    LivingRoom livingRoom
    MainBedroom mainBedroom
}

@DSL
@DisplayName('Kitchen')
class Kitchen extends HeatedRoom {

    StreetWindow street
    @Required SmokeDetector smokeDetector
}

@DSL
@DisplayName('Living room')
class LivingRoom extends HeatedRoom {

    GardenWindow garden
}

@DSL
@DisplayName('Main bedroom')
class MainBedroom extends HeatedRoom {

    GardenWindow garden
}

@DSL
@DisplayName('Garden window')
class GardenWindow extends Window { }

@DSL
@DisplayName('Street window')
class StreetWindow extends Window { }
