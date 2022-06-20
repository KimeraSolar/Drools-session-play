package multicast.senseup.awareness.situation.services.workingMemoryServices.dummies;

import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryBuilder;

public class DummyWorkingMemoryBuilder implements WorkingMemoryBuilder{

    @Override
    public WorkingMemory build(WorkingMemoryConfigurations configurations) {
        System.out.println("Esse método não constroi uma working memory, cuidado!");
        return null;
    }
    
}
