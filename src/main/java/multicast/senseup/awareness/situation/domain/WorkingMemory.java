package multicast.senseup.awareness.situation.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class WorkingMemory implements Serializable {
    public KieSession kieSession;
    public KieBase kieBase;

    private Map<String, String> files = new HashMap<>();
    private String pom;
    private String kmodule;
    private int version;

    final String pkgName;
    final String baseName;
    final String sessionName;

    public KieBuilder kieBuilder;
    public KieFileSystem kieFileSystem;
    public ReleaseId releaseId;
    public KieContainer kieContainer;

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

    public void setFiles(Map<String,String> files) {
        this.files = files;
    }

    public KieBuilder getKieBuilder() {
        return this.kieBuilder;
    }

    public void setKieBuilder(KieBuilder kieBuilder) {
        this.kieBuilder = kieBuilder;
    }

    public KieFileSystem getKieFileSystem() {
        return this.kieFileSystem;
    }

    public void setKieFileSystem(KieFileSystem kieFileSystem) {
        this.kieFileSystem = kieFileSystem;
    }

    public String getPom() {
        return this.pom;
    }

    public void setPom(String pom) {
        this.pom = pom;
    }

    public String getKmodule() {
        return this.kmodule;
    }

    public void setKmodule(String kmodule) {
        this.kmodule = kmodule;
    }

    public ReleaseId getReleaseId() {
        return this.releaseId;
    }

    public void setReleaseId(ReleaseId releaseId) {
        this.releaseId = releaseId;
    }

    public KieContainer getKieContainer() {
        return this.kieContainer;
    }

    public void setKieContainer(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public void updateVersion(){
        version += 1;
    }

    public String getVersion(){
        return "1.0." + version;
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

    public WorkingMemory disposeSession(){
        if(this.getKieSession() != null){
            this.getKieSession().dispose();
            this.setKieSession(null);
        }
        return this;
    }

    public Map<String, String> getFiles(){
        return this.files;
    }

    public void addFile(String fileName, String fileContent){
        this.files.put(fileName, fileContent);
    }

    public void deleteFile(String fileName){
        this.files.remove(fileName);
    }

}
