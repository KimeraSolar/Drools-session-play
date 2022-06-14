package multicast.senseup.awareness.situation.services.factServices.dummies;

import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.services.factServices.FactFinder;

public class DummyFactFinder implements FactFinder {

    @Override
    public Fact find(String factHash) {
        
        System.out.println("Achamos seu fato na working memory");
        return new Fact();
    }
    
}
