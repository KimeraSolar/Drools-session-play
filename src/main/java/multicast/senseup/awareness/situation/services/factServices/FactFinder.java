package multicast.senseup.awareness.situation.services.factServices;

import multicast.senseup.awareness.situation.domain.Fact;

public interface FactFinder {

    public Fact find(String factHash);

}
