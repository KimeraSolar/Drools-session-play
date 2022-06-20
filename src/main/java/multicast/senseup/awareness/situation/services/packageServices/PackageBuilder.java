package multicast.senseup.awareness.situation.services.packageServices;

import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;

public interface PackageBuilder {
    
    public WorkingMemory build(WorkingMemory workingMemory, RulePackage rulePackage);

}
