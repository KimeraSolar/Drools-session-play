package multicast.senseup.awareness.situation.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RulePackage {

    Map<String, RuleForm> rules = new TreeMap<>();

    List<String> includes = new ArrayList<>();

    String pkgName;

    public Map<String, RuleForm> getRules() {
        return this.rules;
    }

    public void setRules(Map<String, RuleForm> rules) {
        this.rules = rules;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public void addRule(RuleForm ruleForm){
        rules.put(ruleForm.getRuleName(), ruleForm);
    }

    public void deleteRule(String ruleName){
        rules.remove(ruleName);
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
    
}
