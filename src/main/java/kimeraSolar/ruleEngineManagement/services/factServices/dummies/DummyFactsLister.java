package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.services.factServices.FactsLister;

public class DummyFactsLister implements FactsLister {
    
    Logger logger = LoggerFactory.getLogger(DummyFactsLister.class);

    @Override
    public List<Fact> list() {
        logger.info("Listamos seus fatos");
        return new LinkedList<>();
    }
    
}
