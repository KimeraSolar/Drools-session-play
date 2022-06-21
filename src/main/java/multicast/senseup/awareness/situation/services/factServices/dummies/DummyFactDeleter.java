package multicast.senseup.awareness.situation.services.factServices.dummies;

import multicast.senseup.awareness.situation.services.factServices.FactDeleter;

public class DummyFactDeleter implements FactDeleter {

    @Override
    public void delete(String factHash) {
        System.out.println("Fato deleteado: " + factHash);
        
    }
    
}
