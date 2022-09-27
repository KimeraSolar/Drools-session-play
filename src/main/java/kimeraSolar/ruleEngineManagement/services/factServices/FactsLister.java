package kimeraSolar.ruleEngineManagement.services.factServices;

import java.util.List;

import kimeraSolar.ruleEngineManagement.domain.Fact;

public interface FactsLister {
    public List<Fact> list();
}
