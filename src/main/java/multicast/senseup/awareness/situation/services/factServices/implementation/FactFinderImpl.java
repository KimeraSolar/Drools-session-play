package multicast.senseup.awareness.situation.services.factServices.implementation;

import java.util.Collection;

import org.kie.api.KieBase;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;

import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.services.factServices.FactFinder;

public class FactFinderImpl implements FactFinder{

    private KieSession kieSession;
    private KieBase kieBase;

    public FactFinderImpl(KieSession kieSession, KieBase kieBase) {
        this.kieSession = kieSession;
        this.kieBase = kieBase;
    }

    public FactFinderImpl() {
        this.kieBase = null;
        this.kieSession = null;
    }

    public KieSession getKieSession() {
        return this.kieSession;
    }

    public void setKieSession(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    public KieBase getKieBase() {
        return this.kieBase;
    }

    public void setKieBase(KieBase kieBase) {
        this.kieBase = kieBase;
    }

    @Override
    public Fact find(String factHash) {
        String factPkg = factHash.substring(0, factHash.lastIndexOf("."));
        String factTypeName = factHash.substring(factHash.lastIndexOf(".") + 1, factHash.lastIndexOf("@"));
        String hashCode = factHash.substring(factHash.lastIndexOf("@") + 1);
        FactType factType = kieBase.getFactType(factPkg, factTypeName);

        Collection<? extends Object> objects = kieSession.getObjects(
            new ObjectFilter() {
                public boolean accept(Object o) {
                    
                    Class< ? > factClass = factType.getFactClass();
                    return factClass.isInstance(o) && o.hashCode() == Integer.parseInt(hashCode);
                }
            }
        );
        Object fact = objects.toArray()[0];
        
        return new Fact(factHash, factType, fact);
    }
    
}
