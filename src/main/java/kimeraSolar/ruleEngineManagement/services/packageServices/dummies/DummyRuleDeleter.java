package kimeraSolar.ruleEngineManagement.services.packageServices.dummies;

import kimeraSolar.ruleEngineManagement.services.packageServices.RuleDeleter;

public class DummyRuleDeleter implements RuleDeleter{

    @Override
    public void delete(String rulePkg, String ruleName) {
        System.out.println("Deleta regra: " + ruleName);
        
    }
    
}
