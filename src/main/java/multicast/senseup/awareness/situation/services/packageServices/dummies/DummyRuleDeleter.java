package multicast.senseup.awareness.situation.services.packageServices.dummies;

import multicast.senseup.awareness.situation.services.packageServices.RuleDeleter;

public class DummyRuleDeleter implements RuleDeleter{

    @Override
    public void delete(String rulePkg, String ruleName) {
        System.out.println("Deleta regra: " + ruleName);
        
    }
    
}
