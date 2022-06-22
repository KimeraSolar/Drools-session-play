package multicast.senseup.awareness.situation.services.packageServices.implementation;

import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.packageServices.RuleDeleter;

public class RuleDeleterImpl implements RuleDeleter{

    WorkingMemory workingMemory;

    public RuleDeleterImpl(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    public void delete(String pkgName, String ruleName) {
        // TODO: retirar a regra do arquivo e não da base (issue #1)
        // https://github.com/KimeraSolar/Drools-session-play/issues/1
        workingMemory.getKieBase().removeRule(pkgName, ruleName);
    }
    
}
