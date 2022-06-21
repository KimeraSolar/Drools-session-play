package multicast.senseup.awareness.situation.services.packageServices.dummies;

import multicast.senseup.awareness.situation.domain.RuleForm;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.packageServices.PackageBuilder;

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
