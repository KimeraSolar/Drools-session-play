package multicast.senseup.awareness.situation.services.factServices.implementation;

import org.kie.api.KieBase;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieSession;

import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.services.factServices.FactInserter;

public class FactInserterImpl implements FactInserter {

    private KieBase kieBase;
    private String pkgName;
    private KieSession kieSession;

    public KieBase getKieBase() {
        return this.kieBase;
    }

    public void setKieBase(KieBase kieBase) {
        this.kieBase = kieBase;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public KieSession getKieSession() {
        return this.kieSession;
    }

    public void setKieSession(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    public FactInserterImpl() {
        this.kieBase = null;
        this.kieSession = null;
        this.pkgName = null;
    }

    public FactInserterImpl(String pkgName, KieBase kieBase, KieSession kieSession) {
        this.kieBase = kieBase;
        this.pkgName = pkgName;
        this.kieSession = kieSession;
    }

    @Override
    public String insert(FactForm factForm) {

        Object obj;
        StringBuilder sbHashString = new StringBuilder();
        try {
            obj = instantiateFact(factForm);
            kieSession.insert(obj);

            sbHashString
                .append(pkgName)
                .append(".")
                .append(factForm.getTypeName())
                .append("@")
                .append(obj.hashCode());

            String hashString = sbHashString.toString();
            return hashString;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object instantiateFact(FactForm factForm) throws InstantiationException, IllegalAccessException {
        FactType factType = kieBase.getFactType(pkgName, factForm.getTypeName());
        Object obj = factType.newInstance();

        factForm.getProperties().forEach((key, value) ->{
            factType.set(obj, key, value);
        });

        return obj;
    }
    
}
