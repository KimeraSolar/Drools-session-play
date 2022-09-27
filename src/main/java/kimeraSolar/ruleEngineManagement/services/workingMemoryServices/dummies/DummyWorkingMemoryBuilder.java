package kimeraSolar.ruleEngineManagement.services.workingMemoryServices.dummies;

import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.WorkingMemoryBuilder;

public class DummyWorkingMemoryBuilder implements WorkingMemoryBuilder{

    @Override
    public WorkingMemory build(WorkingMemoryConfigurations configurations) {
        System.out.println("Esse método não constroi uma working memory, cuidado!");
        return null;
    }
    
}
