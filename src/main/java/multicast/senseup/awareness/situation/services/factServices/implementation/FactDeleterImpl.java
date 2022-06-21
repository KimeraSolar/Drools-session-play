package multicast.senseup.awareness.situation.services.factServices.implementation;


import org.kie.api.runtime.rule.FactHandle;

import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.factServices.FactDeleter;
import multicast.senseup.awareness.situation.services.factServices.FactFinder;

public class FactDeleterImpl implements FactDeleter{

    private WorkingMemory workingMemory;

    public FactDeleterImpl(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    public void delete(String factHash) {
        FactFinder finder = new FactFinderImpl(workingMemory);
        Fact fact = finder.find(factHash);

        FactHandle factHandle = workingMemory.getKieSession().getFactHandle(fact.getFact());
        workingMemory.getKieSession().delete(factHandle);
        
    }
    
}
