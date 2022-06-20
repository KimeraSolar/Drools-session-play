package multicast.senseup.awareness.situation.services.packageServices;

import java.util.List;

import org.kie.api.definition.rule.Rule;

public interface RulesLister {
    public List<Rule> list();
}
