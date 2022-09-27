package kimeraSolar.ruleEngineManagement.domain;

import java.io.Serializable;

import org.json.JSONObject;

public class RuleForm implements Serializable {

    String ruleName;

    String jsonString;

    String sourceCode;

    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getJsonString() {
        return this.jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    private RuleForm(String jsonString, String ruleName, String sourceCode){
        this.jsonString = jsonString;
        this.ruleName = ruleName;
        this.sourceCode = sourceCode;
    }

    public static RuleForm parseJson(String jsonString){
        JSONObject obj = new JSONObject(jsonString);

        String ruleName = obj.getString("ruleName");
        String source = obj.getString("source");

        return new RuleForm(jsonString, ruleName, source);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append("Rule Name: ")
            .append(ruleName)
            .append("\nSource Code: \n")
            .append(sourceCode);
        return stringBuilder.toString();
    }
    
}
