package kimeraSolar.ruleEngineManagement.domain;

import java.util.Map;

import org.json.JSONObject;

public class FactForm {
    String typeName;

    String jsonString;
    
    Map<String, Object> properties;

    private FactForm(String jsonString, String typeName, Map<String, Object> properties){
        this.typeName = typeName;
        this.jsonString = jsonString;
        this.properties = properties;
    }

    public static FactForm parseJson(String jsonString){

        // Faz o parser do Json
        JSONObject obj = new JSONObject(jsonString);

        // Pega as strings do typeName e das properties
        String typeName = obj.getString("typeName");
        String propertiesString = obj.getString("properties");

        // Faz o parser das properties
        Map<String, Object> properties = new JSONObject(propertiesString).toMap();

        // Retorna um novo FactForm
        return new FactForm(jsonString, typeName, properties);

    }

    public String getJson(){
        return this.jsonString;
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

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append("Fact Type: ")
            .append(typeName)
            .append("\nProperties: ")
            .append(properties.toString());
        return stringBuilder.toString();
    }
}
