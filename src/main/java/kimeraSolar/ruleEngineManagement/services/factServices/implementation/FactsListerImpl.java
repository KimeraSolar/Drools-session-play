package kimeraSolar.ruleEngineManagement.services.factServices.implementation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.kie.api.definition.type.FactType;

import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.factServices.FactsLister;
import kimeraSolar.ruleEngineManagement.utils.FactHashGenerator;

public class FactsListerImpl implements FactsLister {

    private WorkingMemory workingMemory;

    public WorkingMemory getWorkingMemory() {
        return this.workingMemory;
    }

    public void setWorkingMemory(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    public FactsListerImpl() {
        this.workingMemory = null;
    }

    public FactsListerImpl(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    public List<Fact> list() {
        Collection<? extends Object> objects = workingMemory.getKieSession().getObjects();
        List<Fact> facts = new LinkedList<>();
        objects.forEach((obj) ->{
            Class<?> objectType = obj.getClass();
            FactType factType = workingMemory.getKieBase().getFactType(objectType.getPackageName(), objectType.getName());
            String factHash = FactHashGenerator.generateFactHash(objectType.getPackageName(), objectType.getName().substring(objectType.getName().lastIndexOf(".") + 1), obj.hashCode());
            facts.add(new Fact(factHash, factType, obj));
        });
        return facts;
    }
    
}
