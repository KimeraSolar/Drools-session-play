package kimeraSolar.ruleEngineManagement.configurations;

public interface RuleEngineConfiguration{
    public String getPkgName();
    public void setPkgName(String pkgName);

    public String getRulesBaseName();
    public void setRulesBaseName(String rulesBaseName);

    public String getSessionName();
    public void setSessionName(String sessionName);
}