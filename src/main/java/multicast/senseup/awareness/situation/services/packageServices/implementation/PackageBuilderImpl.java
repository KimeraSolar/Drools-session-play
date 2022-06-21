package multicast.senseup.awareness.situation.services.packageServices.implementation;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;

import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.packageServices.PackageBuilder;

public class PackageBuilderImpl implements PackageBuilder{

    @Override
    public WorkingMemory build(WorkingMemory workingMemory, RulePackage rulePackage) {
        
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder
            .append("src/main/resources/")
            .append(rulePackage.getPkgName().replace(".", "/"))
            .append("/")
            .append(rulePackage.getFileName())
            .append(".drl");
        String fileName = fileNameBuilder.toString();
        System.out.println(fileName);
        
        workingMemory.updateVersion();

        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        workingMemory.addFile(fileName, rulePackage.getSourceCode());

        kieFileSystem.writePomXML(workingMemory.getPom());
        kieFileSystem.writeKModuleXML(workingMemory.getKmodule());
        for(var file : workingMemory.getFiles().entrySet()){
            kieFileSystem.write(file.getKey(), file.getValue());
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);

        kieBuilder.buildAll();
        
        workingMemory.setReleaseId(kieBuilder.getKieModule().getReleaseId());
        workingMemory.getKieContainer().updateToVersion(kieBuilder.getKieModule().getReleaseId());
        
        return workingMemory;
    }
    
}
