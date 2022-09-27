package kimeraSolar.ruleEngineManagement.configurations;

import java.io.FileNotFoundException;
import java.io.IOException;

import kimeraSolar.ruleEngineManagement.domain.RulePackage;

public interface RuleProvider {
    public RulePackage getRulePackage() throws FileNotFoundException, IOException;
}
