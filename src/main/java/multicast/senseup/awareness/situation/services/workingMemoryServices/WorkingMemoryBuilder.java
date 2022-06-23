package multicast.senseup.awareness.situation.services.workingMemoryServices;

import multicast.senseup.awareness.situation.domain.KmoduleForm;
import multicast.senseup.awareness.situation.domain.PomForm;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;

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
