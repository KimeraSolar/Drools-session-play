package multicast.senseup.awareness.situation.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.model.KieModuleModel;

public class KmoduleForm implements Serializable{

    private List<BaseModelForm> baseModels = new ArrayList<>();

    public List<BaseModelForm> getBaseModels() {
        return this.baseModels;
    }

    public void setBaseModels(List<BaseModelForm> baseModels) {
        this.baseModels = baseModels;
    }

    public void addBaseModel(BaseModelForm baseModel){
        this.baseModels.add(baseModel);
    }

    public void removeBaseModel(BaseModelForm baseModel){
        this.baseModels.remove(baseModel);
    }

    public BaseModelForm getBaseModel(String modelName){
        for(var base : this.baseModels){
            if(base.getBaseName() == modelName) return base;
        }
        return null;
    }

    public String getKmodule(){
        KieServices kieServices = KieServices.Factory.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        for(var base : this.getBaseModels()){
            base.getBaseModel(kieModuleModel);
        }

        return kieModuleModel.toXML();
    }

    @Override
    public String toString(){
        return this.getKmodule();
    }

}
