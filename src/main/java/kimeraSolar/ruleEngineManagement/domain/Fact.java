
package kimeraSolar.ruleEngineManagement.domain;

import org.kie.api.definition.type.FactType;

public class Fact {
    FactType factType;
    String factHash;
    Object fact;

    public Fact() { }

    public Fact(String factHash, FactType factType, Object fact) {
        this.factHash = factHash;
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
