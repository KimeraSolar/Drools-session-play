package kimeraSolar.ruleEngineManagement.domain;

import java.io.Serializable;

import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieSessionModel;

public class SessionModelForm implements Serializable{

    private boolean defaultSession;
    private String sessionName;

    public boolean isDefaultSession() {
        return this.defaultSession;
    }

    public boolean getDefaultSession() {
        return this.defaultSession;
    }

    public void setDefaultSession(boolean defaultSession) {
        this.defaultSession = defaultSession;
    }

    public String getSessionName() {
        return this.sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public KieSessionModel getSessionModel(KieBaseModel kieBaseModel){
        KieSessionModel kieSessionModel = kieBaseModel.newKieSessionModel(this.getSessionName());
        kieSessionModel = kieSessionModel.setDefault(this.isDefaultSession());

        return kieSessionModel;
    }

    
}
