package multicast.senseup.awareness.situation.services.packageServices.implementation;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;

import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.packageServices.RulesLister;

public class RulesListerImpl implements RulesLister {

    WorkingMemory workingMemory;

    public RulesListerImpl(WorkingMemory workingMemory){
        this.workingMemory = workingMemory;
    }

    @Override
    public List<Rule> list() {
        KiePackage kiePackage = workingMemory.getKieBase().getKiePackage(workingMemory.getPkgName());
        
        List<Rule> rules = new ArrayList<>();
        for(Rule rule : kiePackage.getRules()){
            rules.add(rule);
        }
        return rules;
    }
    
}