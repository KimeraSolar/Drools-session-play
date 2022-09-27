package kimeraSolar.ruleEngineManagement.services.workingMemoryServices.implementation;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.kie.internal.marshalling.MarshallerFactory;

import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.WorkingMemorySaver;

public class WorkingMemorySaverImpl implements WorkingMemorySaver{

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

    public WorkingMemorySaverImpl(String fileName, String fileExtension) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    public WorkingMemorySaverImpl() {
        fileName = "workingMemory";
        fileExtension = ".save";
    }

    @Override
    public void save(WorkingMemory workingMemory) {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(fileName + fileExtension);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new Date());

            objectOutputStream.writeObject(workingMemory.getPkgName());
            objectOutputStream.writeObject(workingMemory.getBaseName());
            objectOutputStream.writeObject(workingMemory.getSessionName());
            objectOutputStream.writeObject(workingMemory.getReleaseId());
            objectOutputStream.writeObject(workingMemory.getPomForm());
            objectOutputStream.writeObject(workingMemory.getKmoduleForm());
            objectOutputStream.writeInt(workingMemory.getFiles().size());
            for(var file : workingMemory.getFiles().entrySet()){
                objectOutputStream.writeObject(file.getKey());
                objectOutputStream.writeObject(file.getValue());
            }

            objectOutputStream.writeObject(workingMemory.getKieSession().getSessionConfiguration());
			MarshallerFactory.newMarshaller(workingMemory.getKieBase()).marshall(objectOutputStream, workingMemory.getKieSession());
            objectOutputStream.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
