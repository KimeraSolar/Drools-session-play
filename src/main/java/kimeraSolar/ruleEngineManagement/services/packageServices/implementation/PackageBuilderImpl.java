package kimeraSolar.ruleEngineManagement.services.packageServices.implementation;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.domain.WorkingMemory;
import kimeraSolar.ruleEngineManagement.services.packageServices.PackageBuilder;

public class PackageBuilderImpl implements PackageBuilder{

    Logger logger = LoggerFactory.getLogger(PackageBuilderImpl.class);

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
        logger.info("Package builded in file {}", fileName);

        workingMemory.addPackage(rulePackage.getPkgName());
        
        workingMemory.updateVersion();

        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        if(workingMemory.getFiles().containsKey(fileName)){
            workingMemory.getFiles().remove(fileName);
        }

        workingMemory.addFile(fileName, rulePackage);

        kieFileSystem.writePomXML(workingMemory.getPom());
        kieFileSystem.writeKModuleXML(workingMemory.getKmodule());
        for(var file : workingMemory.getFiles().entrySet()){
            kieFileSystem.write(file.getKey(), file.getValue().getSourceCode());
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);

        kieBuilder.buildAll();
        
        workingMemory.setReleaseId(kieBuilder.getKieModule().getReleaseId());
        workingMemory.getKieContainer().updateToVersion(kieBuilder.getKieModule().getReleaseId());
        
        return workingMemory;
    }
    
}
