package kimeraSolar.ruleEngineManagement.services.factServices.implementation;


import org.kie.api.runtime.rule.FactHandle;

import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.factServices.FactDeleter;
import kimeraSolar.ruleEngineManagement.services.factServices.FactFinder;

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
