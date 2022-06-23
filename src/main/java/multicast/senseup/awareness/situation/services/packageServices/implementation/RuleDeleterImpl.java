package multicast.senseup.awareness.situation.services.packageServices.implementation;

import multicast.senseup.awareness.situation.domain.RuleForm;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.packageServices.PackageBuilder;
import multicast.senseup.awareness.situation.services.packageServices.RuleDeleter;

public class RuleDeleterImpl implements RuleDeleter{

    WorkingMemory workingMemory;

    public RuleDeleterImpl(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    public void delete(String pkgName, String ruleToDelete) {
        for(var file : workingMemory.getFiles().entrySet()){
            RulePackage rulePackage = file.getValue();
            if(rulePackage.getPkgName().equals(pkgName)){
                for(var rule : rulePackage.getRules().entrySet() ){
                    RuleForm ruleForm = rule.getValue();
                    String ruleCode = ruleForm.getSourceCode();
                    
                    int indexOfFirstQuote = ruleCode.indexOf("\"") + 1;
                    int indexOfSecondQuote = ruleCode.indexOf("\"", indexOfFirstQuote);
                    if(indexOfFirstQuote == -1 || indexOfSecondQuote == -1) continue;
                    
                    String ruleName = ruleCode.substring(indexOfFirstQuote, indexOfSecondQuote);
                    System.out.println(ruleName);
                    rulePackage.deleteRule(rule.getKey());
                    PackageBuilder packageBuilder = new PackageBuilderImpl();
                    packageBuilder.build(workingMemory, rulePackage);
                }
            }
        }
    }
    
}
