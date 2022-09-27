package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import java.util.LinkedList;
import java.util.List;

import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.services.factServices.FactsLister;

public class DummyFactsLister implements FactsLister {

    @Override
    public List<Fact> list() {
        System.out.println("Listamos seus fatos");
        return new LinkedList<>();
    }
    
}
