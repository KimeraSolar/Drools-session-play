package multicast.senseup.awareness.situation.services.factServices.implementation;

import org.kie.api.definition.type.FactType;

import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.factServices.FactInserter;
import multicast.senseup.awareness.situation.utils.FactHashGenerator;

public class FactInserterImpl implements FactInserter {

    private WorkingMemory workingMemory;

    public FactInserterImpl() {
        this.workingMemory = null;
    }

    public FactInserterImpl(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

    @Override
    public String insert(FactForm factForm) {

        Object obj;
        
        try {
            obj = instantiateFact(factForm);
            workingMemory.getKieSession().insert(obj);
            String hashString = FactHashGenerator.generateFactHash(workingMemory.getPkgName(), factForm.getTypeName(), obj);
            return hashString;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object instantiateFact(FactForm factForm) throws InstantiationException, IllegalAccessException {
        FactType factType = workingMemory.getKieBase().getFactType(workingMemory.getPkgName(), factForm.getTypeName());
        Object obj = factType.newInstance();

        factForm.getProperties().forEach((key, value) ->{
            factType.set(obj, key, value);
        });

        return obj;
    }
    
}
