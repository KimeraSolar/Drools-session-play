package kimeraSolar.ruleEngineManagement.services.workingMemoryServices.implementation;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.marshalling.MarshallerFactory;

import kimeraSolar.ruleEngineManagement.domain.KmoduleForm;
import kimeraSolar.ruleEngineManagement.domain.PomForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.WorkingMemoryLoader;

public class WorkingMemoryLoaderImpl implements WorkingMemoryLoader{

    private String fileName;
    private String fileExtension;


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public WorkingMemoryLoaderImpl() {
        fileName = "workingMemory";
        fileExtension = ".save";
    }

    public WorkingMemoryLoaderImpl(String fileName, String fileExtension) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    @Override
    public WorkingMemory load(WorkingMemory workingMemory) {
        try{

            if(workingMemory != null){
                workingMemory.disposeSession();
                workingMemory = null;
            }

            FileInputStream fileOutputStream = new FileInputStream(fileName + fileExtension);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileOutputStream);
            System.out.println((Date) objectInputStream.readObject());
            
            String pkgName = (String) objectInputStream.readObject();
            String baseName = (String) objectInputStream.readObject();
            String sessionName = (String) objectInputStream.readObject();
            ReleaseId releaseId = (ReleaseId) objectInputStream.readObject();
            PomForm pom = (PomForm) objectInputStream.readObject();
            KmoduleForm kmodule = (KmoduleForm) objectInputStream.readObject();
            int size = objectInputStream.readInt();

            workingMemory = new WorkingMemory(pkgName, baseName, sessionName);
            for(int i = 0; i < size; i++){
                String fileName = (String) objectInputStream.readObject();
                RulePackage fileContent = (RulePackage) objectInputStream.readObject();
                workingMemory.addFile(fileName, fileContent);
            }

            workingMemory.setReleaseId(releaseId);
            workingMemory.setPom(pom);
            workingMemory.setKmodule(kmodule);
            
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            kieFileSystem.writePomXML(workingMemory.getPom());
            kieFileSystem.writeKModuleXML(workingMemory.getKmodule());
            
            for(var file : workingMemory.getFiles().entrySet()){
                kieFileSystem.write(file.getKey(), file.getValue().getSourceCode());
            }

            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
    
            KieModule kieModule = kieBuilder.getKieModule();
            KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
    
            workingMemory.setKieContainer(kieContainer);

            workingMemory.setKieBase(kieContainer.getKieBase());

            KieSession kieSession = workingMemory.getKieBase().newKieSession();
            
            @SuppressWarnings("unused")
            KieSessionConfiguration kieSessionConfiguration = (KieSessionConfiguration) objectInputStream.readObject();
            
            MarshallerFactory.newMarshaller(workingMemory.getKieBase()).unmarshall(objectInputStream, kieSession);
            workingMemory.setKieSession(kieSession);
            
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return workingMemory;
    }
    
}
