package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kimeraSolar.ruleEngineManagement.services.factServices.FactDeleter;

public class DummyFactDeleter implements FactDeleter {

    Logger logger = LoggerFactory.getLogger(DummyFactDeleter.class);

    @Override
    public void delete(String factHash) {
        logger.info("Fato deleteado: {}", factHash);
        
    }
    
}
