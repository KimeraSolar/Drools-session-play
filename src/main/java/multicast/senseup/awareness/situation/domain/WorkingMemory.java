package multicast.senseup.awareness.situation.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class WorkingMemory implements Serializable {
    public KieSession kieSession;
    public KieBase kieBase;

    public Map<String, String> files = new HashMap<>();
    public List<String> packages = new ArrayList<>();
    public PomForm pom;
    public KmoduleForm kmodule;
    public int version;

    final String pkgName;
    final String baseName;
    final String sessionName;

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

    public List<String> getPackages() {
        return this.packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public void addPackage(String pkgName){
        this.packages.add(pkgName);
    }

    public void removePackage(String pkgName){
        this.packages.remove(pkgName);
    }

    private void setVersion(int version) {
        this.version = version;
        this.getPomForm().setVersion( getVersion() );
    }

    public PomForm getPomForm(){
        return this.pom;
    }

    public String getPom() {
        return this.pom.toString();
    }

    public void setPom(PomForm pom) {
        this.pom = pom;
    }

    public String getKmodule() {
        return this.kmodule.toString();
    }

    public KmoduleForm getKmoduleForm(){
        return this.kmodule;
    }

    public void setKmodule(KmoduleForm kmodule) {
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
        this.setVersion(version + 1);
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
