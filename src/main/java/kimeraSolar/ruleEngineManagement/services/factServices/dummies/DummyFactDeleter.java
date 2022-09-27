package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import kimeraSolar.ruleEngineManagement.services.factServices.FactDeleter;

public class DummyFactDeleter implements FactDeleter {

    @Override
    public void delete(String factHash) {
        System.out.println("Fato deleteado: " + factHash);
        
    }
    
}
