package kimeraSolar.ruleEngineManagement.services.factServices;

import kimeraSolar.ruleEngineManagement.domain.Fact;

public interface FactFinder {

    public Fact find(String factHash);

}
