package kimeraSolar.ruleEngineManagement.services.factServices.dummies;

import kimeraSolar.ruleEngineManagement.domain.FactForm;
import kimeraSolar.ruleEngineManagement.services.factServices.FactInserter;

public class DummyFactInserter implements FactInserter{

    @Override
    public String insert(FactForm factForm) {
        System.out.println("Inserimos seu fato na working memory:");
        System.out.println(factForm);
        return "dummy.factHash@1";
    }
    
}
