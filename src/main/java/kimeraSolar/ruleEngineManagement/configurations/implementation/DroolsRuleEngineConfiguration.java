package kimeraSolar.ruleEngineManagement.configurations.implementation;

import kimeraSolar.ruleEngineManagement.configurations.RuleEngineConfiguration;

public class DroolsRuleEngineConfiguration implements RuleEngineConfiguration {

    private String pkgName;
    private String rulesBaseName;
    private String sessionName;

    @Override
    public String getPkgName() {
        return pkgName;
    }

    @Override
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    @Override
    public String getRulesBaseName() {
        return rulesBaseName;
    }

    @Override
    public void setRulesBaseName(String rulesBaseName) {
        this.rulesBaseName = rulesBaseName;
    }

    @Override
    public String getSessionName() {
        return sessionName;
    }

    @Override
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
    
}
