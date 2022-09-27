package kimeraSolar.ruleEngineManagement.services.packageServices.dummies;

import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.services.packageServices.RuleFileReader;

public class DummyRuleFileReader implements RuleFileReader{

    @Override
    public RulePackage readRuleFile(String ruleFileName) {
        System.out.println("Read file " + ruleFileName);
        return null;
    }
    
}
