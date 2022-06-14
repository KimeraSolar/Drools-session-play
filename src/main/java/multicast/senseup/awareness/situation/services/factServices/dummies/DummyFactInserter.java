package multicast.senseup.awareness.situation.services.factServices.dummies;

import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.services.factServices.FactInserter;

public class DummyFactInserter implements FactInserter{

    @Override
    public String insert(FactForm factForm) {
        System.out.println("Inserimos seu fato na working memory");
        return "dummy.factHash@1";
    }
    
}
