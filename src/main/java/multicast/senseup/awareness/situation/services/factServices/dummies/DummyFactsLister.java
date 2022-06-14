package multicast.senseup.awareness.situation.services.factServices.dummies;

import java.util.LinkedList;
import java.util.List;

import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.services.factServices.FactsLister;

public class DummyFactsLister implements FactsLister {

    @Override
    public List<Fact> list() {
        System.out.println("Listamos seus fatos");
        return new LinkedList<>();
    }
    
}
