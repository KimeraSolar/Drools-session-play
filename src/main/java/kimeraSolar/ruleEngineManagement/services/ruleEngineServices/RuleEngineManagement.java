package kimeraSolar.ruleEngineManagement.services.ruleEngineServices;

import java.util.List;

import org.kie.api.definition.rule.Rule;
import org.kie.api.definition.type.FactType;

import kimeraSolar.ruleEngineManagement.configurations.RuleEngineConfiguration;
import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.domain.FactForm;
import kimeraSolar.ruleEngineManagement.domain.RuleForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;

public interface RuleEngineManagement {
    
    public void setConfigurations(RuleEngineConfiguration ruleEngineConfiguration);
    
    public void resetRuleEngine();

    public boolean isRunning();

    public void startRuleEngine();

    public void stopRuleEngine();

    public void loadWorkingMemory(String fileName);

    public void saveWorkingMemory(String fileName);

    public void insertRule(RulePackage rulePackage, RuleForm ruleForm);

    public void deleteRule(String pkgName, String ruleName);

    public void updateRule(String pkgName, String ruleName, RuleForm ruleForm);
    
    public String insertFact(FactForm factForm);

    public String insertFact(Object factObj);

    public void deleteFact(String FactHash);

    public void insertPackage(RulePackage rulePackage);

    public void deletePackage(RulePackage rulePackage);

    public void deletePackage(String packageName);

    public Fact findFact(String factHash);

    public List<Fact> listFacts();

    public List<Rule> listRules();

    public void fireAllRules();

    public void clear();

    public FactType getFactType(String pkgName, String typeName);

    public WorkingMemory getWorkingMemory();
}
