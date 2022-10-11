package kimeraSolar.ruleEngineManagement.services.factServices;

import kimeraSolar.ruleEngineManagement.domain.FactForm;

public interface FactInserter {
    
    public String insert(FactForm factForm);

    public String insert(Object factObj);
    
}
