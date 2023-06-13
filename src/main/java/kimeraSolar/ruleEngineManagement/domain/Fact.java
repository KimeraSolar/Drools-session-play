
package kimeraSolar.ruleEngineManagement.domain;

import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.rule.FactHandle;

public class Fact {
    FactType factType;
    FactHandle factHandle;
    String factHash;
    Object fact;

    public Fact() { }

    public Fact(String factHash, FactType factType, Object fact) {
        this.factHash = factHash;
        this.factType = factType;
        this.fact = fact;
    }

    public Fact(String factHash, FactHandle factHandle, FactType factType, Object fact) {
        this.factHash = factHash;
        this.factHandle = factHandle;
        this.factType = factType;
        this.fact = fact;
    }

    public FactType getFactType() {
        return this.factType;
    }

    public void setFactType(FactType factType) {
        this.factType = factType;
    }

    public Object getFact() {
        return this.fact;
    }

    public void setFact(Object fact) {
        this.fact = fact;
    }

    public String getFactHash() {
        return this.factHash;
    }

    public void setFactHash(String factHash) {
        this.factHash = factHash;
    }

    public FactHandle getFactHandle(){
        return this.factHandle;
    }

    public void setFactHandle(FactHandle factHandle){
        this.factHandle = factHandle;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append(factHash)
            .append(" : ")
            .append(fact.toString());
        return stringBuilder.toString();
    }

}
