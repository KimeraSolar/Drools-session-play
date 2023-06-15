package kimeraSolar.ruleEngineManagement.services.packageServices.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.packageServices.RulesLister;

public class RulesListerImpl implements RulesLister {

    WorkingMemory workingMemory;
    Logger logger = LoggerFactory.getLogger(RulesListerImpl.class);

    public RulesListerImpl(WorkingMemory workingMemory){
        this.workingMemory = workingMemory;
    }

    @Override
    public List<Rule> list() {

        List<Rule> rules = new ArrayList<>();
        Collection<KiePackage> packages = workingMemory.getKieBase().getKiePackages();
        for(var pkg : packages){
            //logger.info("[Pacote name=" + pkg.getName() + "]");
            for(Rule rule : pkg.getRules()){
                rules.add(rule);
                //logger.info(rule.toString());
            }
        }
        return rules;
    }
    
}
