package multicast.senseup.awareness.situation.services.factServices;

import java.util.List;

import multicast.senseup.awareness.situation.domain.Fact;

public interface FactsLister {
    public List<Fact> list();
}
