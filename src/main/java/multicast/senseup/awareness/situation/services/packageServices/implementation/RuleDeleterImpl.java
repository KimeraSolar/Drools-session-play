package multicast.senseup.awareness.situation.services.packageServices.implementation;

import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.packageServices.RuleDeleter;

public class RuleDeleterImpl implements RuleDeleter{

    WorkingMemory workingMemory;

    public RuleDeleterImpl(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    public void delete(String ruleName) {
        workingMemory.getKieBase().removeRule(workingMemory.getPkgName(), ruleName);
    }
    
}
