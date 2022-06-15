package multicast.senseup.awareness.situation.domain;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

public class WorkingMemory {
    public KieSession kieSession;
    public KieBase kieBase;
    
    final String pkgName;
    final String baseName;
    final String sessionName;

    public WorkingMemory(String pkgName, String baseName, String sessionName) {
        this.pkgName = pkgName;
        this.baseName = baseName;
        this.sessionName = pkgName + sessionName;
    }


    public WorkingMemory(String pkgName, String baseName, String sessionName, KieSession kieSession, KieBase kieBase) {
        this.kieSession = kieSession;
        this.kieBase = kieBase;
        this.pkgName = pkgName;
        this.baseName = baseName;
        this.sessionName = sessionName;
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

    public String getPkgName() {
        return this.pkgName;
    }


    public String getBaseName() {
        return this.baseName;
    }


    public String getSessionName() {
        return this.sessionName;
    }




}
