package multicast.senseup.awareness.situation.services.packageServices;

import java.io.FileNotFoundException;
import java.io.IOException;

import multicast.senseup.awareness.situation.domain.RulePackage;

public interface RuleFileReader {
    public RulePackage readRuleFile(String ruleFileName) throws FileNotFoundException, IOException;
}
