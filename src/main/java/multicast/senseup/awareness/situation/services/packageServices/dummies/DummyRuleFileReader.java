package multicast.senseup.awareness.situation.services.packageServices.dummies;

import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.services.packageServices.RuleFileReader;

public class DummyRuleFileReader implements RuleFileReader{

    @Override
    public RulePackage readRuleFile(String ruleFileName) {
        System.out.println("Read file " + ruleFileName);
        return null;
    }
    
}
