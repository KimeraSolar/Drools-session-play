package kimeraSolar.ruleEngineManagement.services.packageServices;

import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;

public interface PackageBuilder {
    
    public WorkingMemory build(WorkingMemory workingMemory, RulePackage rulePackage);

}
