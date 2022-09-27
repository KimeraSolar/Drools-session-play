package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.services.factServices.FactFinder;

public class DummyFactFinder implements FactFinder {

    @Override
    public Fact find(String factHash) {
        String factPkg = factHash.substring(0, factHash.lastIndexOf("."));
        String factType = factHash.substring(factHash.lastIndexOf(".") + 1, factHash.lastIndexOf("@"));
        String hashCode = factHash.substring(factHash.lastIndexOf("@") + 1);

        System.out.println("Fact Package: " + factPkg);
        System.out.println("Fact Type: " + factType);
        System.out.println("Fact HashCode: " + hashCode);
        System.out.println("Achamos seu fato na working memory");
        return new Fact();
    }
    
}