package kimeraSolar.ruleEngineManagement.services.ruleEngineServices.implementation;

import java.util.List;

import org.kie.api.definition.rule.Rule;
import org.kie.api.definition.type.FactType;

import kimeraSolar.ruleEngineManagement.configurations.RuleEngineConfiguration;
import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.domain.FactForm;
import kimeraSolar.ruleEngineManagement.domain.RuleForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.factServices.FactDeleter;
import kimeraSolar.ruleEngineManagement.services.factServices.FactFinder;
import kimeraSolar.ruleEngineManagement.services.factServices.FactInserter;
import kimeraSolar.ruleEngineManagement.services.factServices.FactsLister;
import kimeraSolar.ruleEngineManagement.services.factServices.implementation.FactDeleterImpl;
import kimeraSolar.ruleEngineManagement.services.factServices.implementation.FactFinderImpl;
import kimeraSolar.ruleEngineManagement.services.factServices.implementation.FactInserterImpl;
import kimeraSolar.ruleEngineManagement.services.factServices.implementation.FactsListerImpl;
import kimeraSolar.ruleEngineManagement.services.packageServices.PackageBuilder;
import kimeraSolar.ruleEngineManagement.services.packageServices.RuleDeleter;
import kimeraSolar.ruleEngineManagement.services.packageServices.RulesLister;
import kimeraSolar.ruleEngineManagement.services.packageServices.implementation.PackageBuilderImpl;
import kimeraSolar.ruleEngineManagement.services.packageServices.implementation.RuleDeleterImpl;
import kimeraSolar.ruleEngineManagement.services.packageServices.implementation.RulesListerImpl;
import kimeraSolar.ruleEngineManagement.services.ruleEngineServices.RuleEngineManagement;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.WorkingMemoryBuilder;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.WorkingMemoryLoader;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.WorkingMemorySaver;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.implementation.DefaultWorkingMemoryBuilder;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.implementation.WorkingMemoryLoaderImpl;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.implementation.WorkingMemorySaverImpl;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.implementation.DefaultWorkingMemoryBuilder.WorkingMemoryConfigurationsImpl;

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
        this.setBuilder(new DefaultWorkingMemoryBuilder());
    }

    public void setBuilder(WorkingMemoryBuilder workingMemoryBuilder){
        this.workingMemoryBuilder = workingMemoryBuilder;
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
    public void updateRule(String rulePkg, String ruleName, RuleForm ruleForm) {
        // boolean wasRunning = isRunning();
        // stopRuleEngine();
        // ruleDeleter.delete(rulePkg, ruleName);
        // RulePackage rulePackage = new RulePackage();
        // rulePackage.setFileName(ruleName);
        // rulePackage.addRule(ruleForm);
        // if(wasRunning) startRuleEngine();
        throw new UnsupportedOperationException("Unimplemented method 'updateRule'");
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
        throw new UnsupportedOperationException("Unimplemented method 'deletePackage'");
    }

    @Override
    public void deletePackage(String packageName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePackage'");        
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

    @Override
    public FactType getFactType(String pkgName, String typeName) {
        FactType factType = workingMemory.getKieBase().getFactType(pkgName, typeName);
        return factType;
    }

    @Override
    public String insertFact(Object factObj) {
        return factInserter.insert(factObj);
    }

    @Override
    public WorkingMemory getWorkingMemory() {
        return this.workingMemory;
    }
    
}
