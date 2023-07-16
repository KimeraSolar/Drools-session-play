package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.services.factServices.FactFinder;

public class DummyFactFinder implements FactFinder {

    Logger logger = LoggerFactory.getLogger(DummyFactFinder.class);

    @Override
    public Fact find(String factHash) {
        String factPkg = factHash.substring(0, factHash.lastIndexOf("."));
        String factType = factHash.substring(factHash.lastIndexOf(".") + 1, factHash.lastIndexOf("@"));
        String hashCode = factHash.substring(factHash.lastIndexOf("@") + 1);

        logger.info("Fact Package: {}", factPkg);
        logger.info("Fact Type: {}", factType);
        logger.info("Fact HashCode: {}", hashCode);
        logger.info("Achamos seu fato na working memory");
        return new Fact();
    }
    
}
