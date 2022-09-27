package kimeraSolar.ruleEngineManagement.services.packageServices.implementation;

import java.util.ArrayList;
import java.util.stream.Collectors;

import kimeraSolar.ruleEngineManagement.domain.RuleForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.packageServices.PackageBuilder;
import kimeraSolar.ruleEngineManagement.services.packageServices.RuleDeleter;

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

                var exclude = new ArrayList<> (rulePackage.getRules().entrySet().stream()
                .filter(entry -> {
                    RuleForm ruleForm = entry.getValue();
                    String ruleCode = ruleForm.getSourceCode();

                    int indexOfFirstQuote = ruleCode.indexOf("\"") + 1;
                    int indexOfSecondQuote = ruleCode.indexOf("\"", indexOfFirstQuote);
                    if(indexOfFirstQuote == -1 || indexOfSecondQuote == -1) return false;

                    String ruleName = ruleCode.substring(indexOfFirstQuote, indexOfSecondQuote);
                    if (entry.getKey().equals(ruleToDelete) || ruleName.equals(ruleToDelete)){
                        return true;
                    }
                    return false;
                }).map(entry -> entry.getKey())
                .collect(Collectors.toList()));
                
                if(exclude.size() == 0) continue;
                
                rulePackage.deleteRule(exclude.get(0));
                PackageBuilder packageBuilder = new PackageBuilderImpl();
                workingMemory = packageBuilder.build(workingMemory, rulePackage);
                return;
                
            }
        }
    }
    
}
