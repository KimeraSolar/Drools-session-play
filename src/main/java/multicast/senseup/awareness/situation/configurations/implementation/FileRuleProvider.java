package multicast.senseup.awareness.situation.configurations.implementation;

import java.io.FileNotFoundException;
import java.io.IOException;

import multicast.senseup.awareness.situation.configurations.RuleProvider;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.services.packageServices.RuleFileReader;
import multicast.senseup.awareness.situation.services.packageServices.implementation.RuleFileReaderImpl;

public class FileRuleProvider implements RuleProvider {

    String fileName;

    @Override
    public RulePackage getRulePackage() throws FileNotFoundException, IOException {
        RuleFileReader ruleFileReader = new RuleFileReaderImpl();
        return ruleFileReader.readRuleFile(fileName);
    }
    
}
