package multicast.senseup.awareness.situation.services.packageServices.implementation;

import org.kie.api.KieServices;

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

        KieServices kieServices = KieServices.Factory.get();

        if(workingMemory.getKieFileSystem() == null){
            workingMemory.setKieFileSystem(kieServices.newKieFileSystem());
            workingMemory.getKieFileSystem().writePomXML(workingMemory.getPom());
            workingMemory.getKieFileSystem().writeKModuleXML(workingMemory.getKmodule());
            for(var file : workingMemory.getFiles().entrySet()){
                workingMemory.getKieFileSystem().write(file.getKey(), file.getValue());
            }

            workingMemory.setKieBuilder(kieServices.newKieBuilder(workingMemory.getKieFileSystem()));
            workingMemory.setKieContainer(kieServices.newKieContainer(workingMemory.releaseId));
        }

        workingMemory.getKieFileSystem().write(fileName, rulePackage.getSourceCode());
        workingMemory.getKieBuilder().buildAll();

        workingMemory.updateVersion();
        workingMemory.getKieContainer().updateToVersion(kieServices.newReleaseId(workingMemory.getPkgName(), workingMemory.getBaseName().replace(".", "-"), workingMemory.getVersion()));
        
        workingMemory.addFile(fileName, rulePackage.getSourceCode());
        return workingMemory;
    }
    
}
