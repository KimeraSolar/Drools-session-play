package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kimeraSolar.ruleEngineManagement.domain.FactForm;
import kimeraSolar.ruleEngineManagement.services.factServices.FactInserter;

public class DummyFactInserter implements FactInserter{

    Logger logger = LoggerFactory.getLogger(DummyFactInserter.class);

    @Override
    public String insert(FactForm factForm) {
        logger.info("Inserimos seu fato na working memory: {}", factForm.toString());
        return "dummy.factHash@1";
    }

    @Override
    public String insert(Object factObj) {
        logger.info("Inserimos seu fato na working memory: {}", factObj.toString());
        return "dummy.factHash@1";
    }
    
}
