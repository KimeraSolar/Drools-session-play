package kimeraSolar.ruleEngineManagement.services.workingMemoryServices;

import kimeraSolar.ruleEngineManagement.domain.KmoduleForm;
import kimeraSolar.ruleEngineManagement.domain.PomForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;

public interface WorkingMemoryBuilder {
    public interface WorkingMemoryConfigurations{
        public PomForm getPom();

        public RulePackage getSourceCode();

        public KmoduleForm getConfigurations();

        public String getPkgName();

        public void setPkgName(String pkgName);

        public String getDefaultSessionName();

        public String getDefaultBaseName();

        public void setDefaultBaseName(String defaultBaseName);
    }

    public WorkingMemory build(WorkingMemoryConfigurations configurations);
}
