package multicast.senseup.awareness.situation.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.conf.EventProcessingOption;

public class BaseModelForm implements Serializable{
    private boolean defaultBase;
    private String baseName;
    private EventProcessingOption eventMode;
    private List<String> packages = new ArrayList<>();
    private List<String> includes = new ArrayList<>();
    private List<SessionModelForm> sessions = new ArrayList<>();


    public boolean isDefaultBase() {
        return this.defaultBase;
    }

    public boolean getDefaultBase() {
        return this.defaultBase;
    }

    public void setDefaultBase(boolean defaultBase) {
        this.defaultBase = defaultBase;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public EventProcessingOption getEventMode() {
        return this.eventMode;
    }

    public void setEventMode(EventProcessingOption eventMode) {
        this.eventMode = eventMode;
    }

    public List<String> getPackages() {
        return this.packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public void addPackage(String packageName){
        this.packages.add(packageName);
    }

    public void removePackage(String packageName){
        this.packages.remove(packageName);
    }

    public List<String> getIncludes() {
        return this.includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public void addInclude(String includeName){
        this.includes.add(includeName);
    }

    public void removeInclude(String includeName){
        this.includes.remove(includeName);
    }

    public List<SessionModelForm> getSessions() {
        return this.sessions;
    }

    public void setSessions(List<SessionModelForm> sessions) {
        this.sessions = sessions;
    }

    public void addSession(SessionModelForm session){
        this.sessions.add(session);
    }

    public void removeSession(SessionModelForm session){
        this.sessions.remove(session);
    }

    public KieBaseModel getBaseModel(KieModuleModel kieModuleModel){
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(this.getBaseName());
        kieBaseModel = kieBaseModel.setDefault( this.isDefaultBase() );
        kieBaseModel = kieBaseModel.setEventProcessingMode( this.getEventMode() );

        for(String include : this.getIncludes()){
            kieBaseModel.addInclude(include);
        }

        for(String packageName : this.getPackages()){
            kieBaseModel.addPackage(packageName);
        }

        for(SessionModelForm sessionModel : this.getSessions()){
            sessionModel.getSessionModel(kieBaseModel);
        }

        return kieBaseModel;
    }

}
