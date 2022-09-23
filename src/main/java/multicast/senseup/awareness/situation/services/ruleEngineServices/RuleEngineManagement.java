package multicast.senseup.awareness.situation.services.ruleEngineServices;

import java.util.List;

import org.kie.api.definition.rule.Rule;

import multicast.senseup.awareness.situation.configurations.RuleEngineConfiguration;
import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.domain.RuleForm;
import multicast.senseup.awareness.situation.domain.RulePackage;

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

    public void deleteFact(String FactHash);

    public void insertPackage(RulePackage rulePackage);

    public void deletePackage(RulePackage rulePackage);

    public void deletePackage(String packageName);

    public Fact findFact(String factHash);

    public List<Fact> listFacts();

    public List<Rule> listRules();

    public void fireAllRules();

    public void clear();
}
