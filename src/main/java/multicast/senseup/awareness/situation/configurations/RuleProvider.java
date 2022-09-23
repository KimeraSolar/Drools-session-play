package multicast.senseup.awareness.situation.configurations;

import java.io.FileNotFoundException;
import java.io.IOException;

import multicast.senseup.awareness.situation.domain.RulePackage;

public interface RuleProvider {
    public RulePackage getRulePackage() throws FileNotFoundException, IOException;
}
