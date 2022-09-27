package kimeraSolar.ruleEngineManagement.services.packageServices;

import java.io.FileNotFoundException;
import java.io.IOException;

import kimeraSolar.ruleEngineManagement.domain.RulePackage;

public interface RuleFileReader {
    public RulePackage readRuleFile(String ruleFileName) throws FileNotFoundException, IOException;
}
