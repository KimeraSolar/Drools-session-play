package kimeraSolar.ruleEngineManagement.services.packageServices.dummies;

import kimeraSolar.ruleEngineManagement.domain.RuleForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.packageServices.PackageBuilder;

public class DummyPackageBuilder implements PackageBuilder{

    @Override
    public WorkingMemory build(WorkingMemory workingMemory, RulePackage rulePackage) {
        System.out.println("Build Package: " + rulePackage.getPkgName() );
        System.out.println("Rules: ");
        for(RuleForm rule : rulePackage.getRules().values()){
            System.out.println("\t" + rule.getRuleName());
        }
        System.out.println("Source Code:");
        System.out.println(rulePackage.toString());
        return workingMemory;
    }
    
}
