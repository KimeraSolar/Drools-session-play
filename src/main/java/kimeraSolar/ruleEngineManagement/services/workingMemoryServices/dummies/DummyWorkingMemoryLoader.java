package kimeraSolar.ruleEngineManagement.services.workingMemoryServices.dummies;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;

import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.workingMemoryServices.WorkingMemoryLoader;

public class DummyWorkingMemoryLoader implements WorkingMemoryLoader{

    @Override
    public WorkingMemory load(WorkingMemory workingMemory) {
        try{
            FileInputStream fileOutputStream = new FileInputStream("dummyWorkingMemory.save");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileOutputStream);
            System.out.println((String) objectInputStream.readObject());
            System.out.println((Date) objectInputStream.readObject());
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
}
