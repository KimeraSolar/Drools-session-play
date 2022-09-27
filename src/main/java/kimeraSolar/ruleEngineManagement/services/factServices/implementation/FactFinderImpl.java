package kimeraSolar.ruleEngineManagement.services.factServices.implementation;

import java.util.Collection;

import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.ObjectFilter;

import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.factServices.FactFinder;
import kimeraSolar.ruleEngineManagement.utils.FactHashGenerator;

public class FactFinderImpl implements FactFinder{

    private WorkingMemory workingMemory;

    public FactFinderImpl(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    public Fact find(String factHash) {
        String factPkg = FactHashGenerator.getPkgName(factHash);
        String factTypeName = FactHashGenerator.getTypeName(factHash);
        String hashCode = FactHashGenerator.getObjectHash(factHash);
        FactType factType = workingMemory.getKieBase().getFactType(factPkg, factTypeName);

        Collection<? extends Object> objects = workingMemory.getKieSession().getObjects(
            new ObjectFilter() {
                public boolean accept(Object o) {
                    
                    Class< ? > factClass = factType.getFactClass();
                    return factClass.isInstance(o) && o.hashCode() == Integer.parseInt(hashCode);
                }
            }
        );
        Object fact = objects.toArray()[0];
        
        return new Fact(factHash, factType, fact);
    }
    
}
