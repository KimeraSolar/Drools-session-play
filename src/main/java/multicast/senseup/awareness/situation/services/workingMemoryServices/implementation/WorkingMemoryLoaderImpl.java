package multicast.senseup.awareness.situation.services.workingMemoryServices.implementation;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.marshalling.MarshallerFactory;

import multicast.senseup.awareness.situation.domain.PomForm;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryLoader;

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
            PomForm pom = (PomForm) objectInputStream.readObject();
            String kmodule = (String) objectInputStream.readObject();
            int size = objectInputStream.readInt();

            workingMemory = new WorkingMemory(pkgName, baseName, sessionName);
            for(int i = 0; i < size; i++){
                String fileName = (String) objectInputStream.readObject();
                String fileContent = (String) objectInputStream.readObject();
                workingMemory.addFile(fileName, fileContent);
            }

            workingMemory.setPom(pom);
            workingMemory.setKmodule(kmodule);
            workingMemory.setKieBase((KieBase) objectInputStream.readObject());
            
            KieSessionConfiguration kieSessionConfiguration = (KieSessionConfiguration) objectInputStream.readObject();
            workingMemory.setKieSession(MarshallerFactory.newMarshaller(workingMemory.getKieBase()).unmarshall(objectInputStream, kieSessionConfiguration, null));
            
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return workingMemory;
    }
    
}
