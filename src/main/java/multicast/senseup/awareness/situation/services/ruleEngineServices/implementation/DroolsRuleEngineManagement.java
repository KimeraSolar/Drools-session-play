package multicast.senseup.awareness.situation.services.ruleEngineServices.implementation;

import java.util.List;

import org.kie.api.definition.rule.Rule;

import multicast.senseup.awareness.situation.configurations.RuleEngineConfiguration;
import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.domain.RuleForm;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.factServices.FactDeleter;
import multicast.senseup.awareness.situation.services.factServices.FactFinder;
import multicast.senseup.awareness.situation.services.factServices.FactInserter;
import multicast.senseup.awareness.situation.services.factServices.FactsLister;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactDeleterImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactFinderImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactInserterImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactsListerImpl;
import multicast.senseup.awareness.situation.services.packageServices.PackageBuilder;
import multicast.senseup.awareness.situation.services.packageServices.RuleDeleter;
import multicast.senseup.awareness.situation.services.packageServices.RulesLister;
import multicast.senseup.awareness.situation.services.packageServices.implementation.PackageBuilderImpl;
import multicast.senseup.awareness.situation.services.packageServices.implementation.RuleDeleterImpl;
import multicast.senseup.awareness.situation.services.packageServices.implementation.RulesListerImpl;
import multicast.senseup.awareness.situation.services.ruleEngineServices.RuleEngineManagement;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryBuilder;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryLoader;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemorySaver;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.DefaultWorkingMemoryBuilder;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemoryLoaderImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemorySaverImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.DefaultWorkingMemoryBuilder.WorkingMemoryConfigurationsImpl;

public class DroolsRuleEngineManagement implements RuleEngineManagement{

    private RuleEngineConfiguration ruleEngineConfiguration;
 
    private WorkingMemory workingMemory;
    private WorkingMemoryBuilder workingMemoryBuilder;
    private WorkingMemorySaver workingMemorySaver;
    private WorkingMemoryLoader workingMemoryLoader;
    private String memorySaveExtension = ".save";

    private FactFinder factFinder;
    private FactInserter factInserter;
    private FactDeleter factDeleter;
    private FactsLister factsLister;

    private RuleDeleter ruleDeleter;
    private RulesLister rulesLister;

    public DroolsRuleEngineManagement(){
        workingMemoryBuilder = new DefaultWorkingMemoryBuilder();
    }

    private void setWorkingMemory(WorkingMemory workingMemory){
        this.workingMemory = workingMemory;
        this.factFinder = new FactFinderImpl(workingMemory);
        this.factInserter = new FactInserterImpl(workingMemory);
        this.factDeleter = new FactDeleterImpl(workingMemory);
        this.factsLister = new FactsListerImpl(workingMemory);

        this.ruleDeleter = new RuleDeleterImpl(workingMemory);
        this.rulesLister = new RulesListerImpl(workingMemory);
    }

    @Override
    public void resetRuleEngine() {
        boolean wasRunning = isRunning();
        clear();
        this.setWorkingMemory(
            workingMemoryBuilder.build(
                new WorkingMemoryConfigurationsImpl(
                ruleEngineConfiguration.getPkgName(), 
                ruleEngineConfiguration.getRulesBaseName()
            ))
        );

        if(wasRunning) startRuleEngine();
        
    }

    @Override
    public void startRuleEngine() {
        if(workingMemory != null) workingMemory.run();
        
    }

    @Override
    public void stopRuleEngine() {
        if(workingMemory != null) workingMemory.stop();
        
    }

    @Override
    public void loadWorkingMemory(String fileName) {
        boolean wasRunning = isRunning();
        stopRuleEngine();
        workingMemoryLoader = new WorkingMemoryLoaderImpl(fileName, memorySaveExtension);
        setWorkingMemory(workingMemoryLoader.load(workingMemory));
        if(wasRunning) startRuleEngine();
    }

    @Override
    public void saveWorkingMemory(String fileName) {
        workingMemorySaver = new WorkingMemorySaverImpl(fileName, memorySaveExtension);
        workingMemorySaver.save(workingMemory);
    }

    @Override
    public void insertRule(RulePackage rulePackage, RuleForm ruleForm) {
        rulePackage.addRule(ruleForm);
        insertPackage(rulePackage);
        
    }

    @Override
    public void deleteRule(String rulePkg, String ruleName) {
        boolean wasRunning = isRunning();
        stopRuleEngine();
        ruleDeleter.delete(rulePkg, ruleName);
        if(wasRunning) startRuleEngine();
        
    }

    @Override
    public void updateRule(String rulePkg, String ruleHash, RuleForm ruleForm) {
        // TODO Auto-generated method stub
    }

    @Override
    public String insertFact(FactForm factForm) {
        return factInserter.insert(factForm);
        
    }

    @Override
    public void deleteFact(String factHash) {
        factDeleter.delete(factHash);
    }

    @Override
    public void insertPackage(RulePackage rulePackage) {
        boolean wasRunning = isRunning();
        stopRuleEngine();
        PackageBuilder packageBuilder = new PackageBuilderImpl();
        packageBuilder.build(workingMemory, rulePackage);
        if(wasRunning) startRuleEngine();
        
    }

    @Override
    public void deletePackage(RulePackage rulePackage) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deletePackage(String packageName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Fact findFact(String factHash) {
        return factFinder.find(factHash);
    }

    @Override
    public List<Fact> listFacts() {
        return factsLister.list();
    }

    @Override
    public List<Rule> listRules() {
        return rulesLister.list();
    }

    @Override
    public void setConfigurations(RuleEngineConfiguration ruleEngineConfiguration) {
        this.ruleEngineConfiguration = ruleEngineConfiguration;
        
    }

    @Override
    public boolean isRunning() {
        boolean isRunning = false;
        if (workingMemory != null) isRunning = workingMemory.isRunning();
        return isRunning;
    }

    @Override
    public void fireAllRules() {
        if(workingMemory != null) workingMemory.getKieSession().fireAllRules();
        
    }

    @Override
    public void clear(){
        if(workingMemory != null) workingMemory.disposeSession();
        setWorkingMemory(null);
    }
    
}
