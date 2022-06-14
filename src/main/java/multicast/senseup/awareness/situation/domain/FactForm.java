package multicast.senseup.awareness.situation.domain;

import java.util.Map;

public class FactForm {
    String typeName;

    String JsonString;
    
    Map<String, Object> properties;

    private FactForm(String JSON){

    }

    public static FactForm parseJson(String JSON){
        return new FactForm(JSON);
    }

    public String getJson(){
        return this.JsonString;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Map<String,Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String,Object> properties) {
        this.properties = properties;
    }
}
