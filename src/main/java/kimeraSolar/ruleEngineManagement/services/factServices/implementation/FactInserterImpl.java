package kimeraSolar.ruleEngineManagement.services.factServices.implementation;

import org.kie.api.definition.type.FactType;

import kimeraSolar.ruleEngineManagement.domain.FactForm;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.factServices.FactInserter;
import kimeraSolar.ruleEngineManagement.utils.FactHashGenerator;

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
            return insert(obj);

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

    @Override
    public String insert(Object factObj) {
        workingMemory.getKieSession().insert(factObj);
        String hashString = FactHashGenerator.generateFactHash(workingMemory.getPkgName(), factObj.getClass().getSimpleName(), factObj);
        return hashString;
    }
    
}
