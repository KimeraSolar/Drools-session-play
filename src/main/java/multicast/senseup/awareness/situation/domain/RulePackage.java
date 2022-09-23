package multicast.senseup.awareness.situation.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RulePackage implements Serializable{

    List <RuleForm> rules = new ArrayList<>();

    List<String> includes = new ArrayList<>();

    String pkgName;

    String fileName;

    public Map<String, RuleForm> getRules() {
        Map<String, RuleForm> rulesMap = new TreeMap<>();
        for(RuleForm rule : this.rules){
            rulesMap.put(rule.getRuleName(), rule);
        } 
        return rulesMap;
    }

    public void setRules(List <RuleForm> rules) {
        this.rules = rules;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName.replace(".", "");
    }

    public void addRule(RuleForm ruleForm){
        rules.add(ruleForm);
    }

    public void deleteRule(String ruleName){
        for(RuleForm rule : this.rules){
            if(ruleName.equals(rule.getRuleName())){ 
                this.rules.remove(rule);
                break;
            }
        }
    }

    public List<String> getIncludes() {
        return this.includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public void addInclude(String include){
        this.includes.add(include);
    }

    public void removeInclude(String include){
        this.includes.remove(include);
    }

    public String getSourceCode(){
        StringBuilder sourceCodeBuilder = new StringBuilder();

        sourceCodeBuilder
            .append("package ").append( getPkgName() ).append(";\n")
            .append("\n");
        for(String include : getIncludes()){
            sourceCodeBuilder.append("import ").append(include).append(";\n");
        }
        sourceCodeBuilder
            .append("\n");
        for(RuleForm ruleForm : this.rules){
            sourceCodeBuilder
                .append(ruleForm.getSourceCode())
                .append("\n");
        }

        return sourceCodeBuilder.toString();
    }

    public String toString(){
        return this.getSourceCode();
    }
    
}
