package onboarding.smarthome.client;

import onboarding.smarthome.api.Window;

/** External state lookup kept separate from the durable configured Model. */
@FunctionalInterface
public interface WindowStateService {

    /** Returns the current state for one generic Domain API window. */
    String stateFor(Window window);
}
