package multicast.senseup.awareness.situation.services.packageServices.dummies;

import java.util.LinkedList;
import java.util.List;

import org.kie.api.definition.rule.Rule;

import multicast.senseup.awareness.situation.services.packageServices.RulesLister;

public class DummyRulesLister implements RulesLister{
    public List<Rule> list(){
        System.out.println("Lista suas regras");
        return new LinkedList<>();
    }
}
