package kimeraSolar.ruleEngineManagement.configurations.implementation;

import java.io.FileNotFoundException;
import java.io.IOException;

import kimeraSolar.ruleEngineManagement.configurations.RuleProvider;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.services.packageServices.RuleFileReader;
import kimeraSolar.ruleEngineManagement.services.packageServices.implementation.RuleFileReaderImpl;

public class FileRuleProvider implements RuleProvider {

    String fileName;

    @Override
    public RulePackage getRulePackage() throws FileNotFoundException, IOException {
        RuleFileReader ruleFileReader = new RuleFileReaderImpl();
        return ruleFileReader.readRuleFile(fileName);
    }
    
}
