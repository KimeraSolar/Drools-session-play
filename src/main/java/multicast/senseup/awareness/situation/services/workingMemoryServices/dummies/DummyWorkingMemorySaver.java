package multicast.senseup.awareness.situation.services.workingMemoryServices.dummies;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemorySaver;

public class DummyWorkingMemorySaver implements WorkingMemorySaver {

    @Override
    public void save(WorkingMemory workingMemory) {
        System.out.println("Sua working memory foi salva");
        System.out.println(workingMemory.getKieBase());
        try{
        FileOutputStream fileOutputStream = new FileOutputStream("dummyWorkingMemory.save");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject("Sua working memory foi salva! ");
        objectOutputStream.writeObject(new Date());
        objectOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
